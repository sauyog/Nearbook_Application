package org.briarproject.masterproject.android.sharing;

import static org.briarproject.bramble.util.LogUtils.logException;
import static org.briarproject.masterproject.api.blog.BlogManager.CLIENT_ID;
import static java.util.logging.Level.WARNING;

import org.briarproject.bramble.api.contact.Contact;
import org.briarproject.bramble.api.db.DatabaseExecutor;
import org.briarproject.bramble.api.db.DbException;
import org.briarproject.bramble.api.event.Event;
import org.briarproject.bramble.api.event.EventBus;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.bramble.api.sync.ClientId;
import org.briarproject.masterproject.android.controller.handler.ExceptionHandler;
import org.briarproject.masterproject.api.blog.Blog;
import org.briarproject.masterproject.api.blog.BlogSharingManager;
import org.briarproject.masterproject.api.blog.event.BlogInvitationRequestReceivedEvent;
import org.briarproject.masterproject.api.sharing.SharingInvitationItem;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.Collection;
import java.util.concurrent.Executor;

import javax.inject.Inject;

@NotNullByDefault
class BlogInvitationControllerImpl
        extends InvitationControllerImpl<SharingInvitationItem>
        implements BlogInvitationController {

    private final BlogSharingManager blogSharingManager;

    @Inject
    BlogInvitationControllerImpl(@DatabaseExecutor Executor dbExecutor,
                                 LifecycleManager lifecycleManager, EventBus eventBus,
                                 BlogSharingManager blogSharingManager) {
        super(dbExecutor, lifecycleManager, eventBus);
        this.blogSharingManager = blogSharingManager;
    }

    @Override
    public void eventOccurred(Event e) {
        super.eventOccurred(e);

        if (e instanceof BlogInvitationRequestReceivedEvent) {
            LOG.info("Blog invitation received, reloading");
            listener.loadInvitations(false);
        }
    }

    @Override
    protected ClientId getShareableClientId() {
        return CLIENT_ID;
    }

    @Override
    protected Collection<SharingInvitationItem> getInvitations() throws DbException {
        return blogSharingManager.getInvitations();
    }

    @Override
    public void respondToInvitation(SharingInvitationItem item, boolean accept,
                                    ExceptionHandler<DbException> handler) {
        runOnDbThread(() -> {
            try {
                Blog f = (Blog) item.getShareable();
                for (Contact c : item.getNewSharers()) {
                    // TODO: What happens if a contact has been removed?
                    blogSharingManager.respondToInvitation(f, c, accept);
                }
            } catch (DbException e) {
                logException(LOG, WARNING, e);
                handler.onException(e);
            }
        });
    }

}