package org.briarproject.masterproject.android.sharing;

import org.briarproject.bramble.api.contact.Contact;
import org.briarproject.bramble.api.db.DatabaseExecutor;
import org.briarproject.bramble.api.db.DbException;
import org.briarproject.bramble.api.event.Event;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.api.blog.BlogInvitationResponse;
import org.briarproject.masterproject.api.blog.BlogSharingManager;
import org.briarproject.masterproject.api.blog.event.BlogInvitationResponseReceivedEvent;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import java.util.Collection;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class BlogSharingStatusActivity extends SharingStatusActivity {

    // Fields that are accessed from background threads must be volatile
    @Inject
    protected volatile BlogSharingManager blogSharingManager;

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void eventOccurred(Event e) {
        super.eventOccurred(e);
        if (e instanceof BlogInvitationResponseReceivedEvent) {
            BlogInvitationResponseReceivedEvent r =
                    (BlogInvitationResponseReceivedEvent) e;
            BlogInvitationResponse h = r.getMessageHeader();
            if (h.getShareableId().equals(getGroupId()) && h.wasAccepted()) {
                loadSharedWith();
            }
        }
    }

    @Override
    int getInfoText() {
        return R.string.sharing_status_blog;
    }

    @Override
    @DatabaseExecutor
    protected Collection<Contact> getSharedWith() throws DbException {
        return blogSharingManager.getSharedWith(getGroupId());
    }

}
