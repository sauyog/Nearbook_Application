package org.briarproject.briar.test;

import org.briarproject.bramble.BrambleCoreIntegrationTestEagerSingletons;
import org.briarproject.bramble.BrambleCoreModule;
import org.briarproject.bramble.api.contact.ContactManager;
import org.briarproject.bramble.api.db.DatabaseComponent;
import org.briarproject.bramble.api.identity.AuthorFactory;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.bramble.api.properties.TransportPropertyManager;
import org.briarproject.bramble.api.system.Clock;
import org.briarproject.bramble.mailbox.ModularMailboxModule;
import org.briarproject.bramble.test.BrambleCoreIntegrationTestModule;
import org.briarproject.bramble.test.BrambleIntegrationTestComponent;
import org.briarproject.bramble.test.TestDnsModule;
import org.briarproject.bramble.test.TestPluginConfigModule;
import org.briarproject.bramble.test.TestSocksModule;
import org.briarproject.bramble.test.TimeTravel;
import org.briarproject.briar.attachment.AttachmentModule;
import org.briarproject.briar.autodelete.AutoDeleteModule;
import org.briarproject.briar.avatar.AvatarModule;
import org.briarproject.briar.blog.BlogModule;
import org.briarproject.briar.client.BriarClientModule;
import org.briarproject.briar.conversation.ConversationModule;
import org.briarproject.briar.forum.ForumModule;
import org.briarproject.briar.identity.IdentityModule;
import org.briarproject.briar.introduction.IntroductionModule;
import org.briarproject.briar.messaging.MessagingModule;
import org.briarproject.briar.privategroup.PrivateGroupModule;
import org.briarproject.briar.privategroup.invitation.GroupInvitationModule;
import org.briarproject.briar.sharing.SharingModule;
import org.briarproject.masterproject.api.attachment.AttachmentReader;
import org.briarproject.masterproject.api.autodelete.AutoDeleteManager;
import org.briarproject.masterproject.api.avatar.AvatarManager;
import org.briarproject.masterproject.api.blog.BlogFactory;
import org.briarproject.masterproject.api.blog.BlogManager;
import org.briarproject.masterproject.api.blog.BlogSharingManager;
import org.briarproject.masterproject.api.client.MessageTracker;
import org.briarproject.masterproject.api.conversation.ConversationManager;
import org.briarproject.masterproject.api.forum.ForumManager;
import org.briarproject.masterproject.api.forum.ForumSharingManager;
import org.briarproject.masterproject.api.introduction.IntroductionManager;
import org.briarproject.masterproject.api.messaging.MessagingManager;
import org.briarproject.masterproject.api.messaging.PrivateMessageFactory;
import org.briarproject.masterproject.api.privategroup.PrivateGroupManager;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationFactory;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        BrambleCoreIntegrationTestModule.class,
        BrambleCoreModule.class,
        AttachmentModule.class,
        AutoDeleteModule.class,
        AvatarModule.class,
        BlogModule.class,
        BriarClientModule.class,
        ConversationModule.class,
        ForumModule.class,
        GroupInvitationModule.class,
        IdentityModule.class,
        IntroductionModule.class,
        MessagingModule.class,
        PrivateGroupModule.class,
        SharingModule.class,
        ModularMailboxModule.class,
        TestDnsModule.class,
        TestSocksModule.class,
        TestPluginConfigModule.class,
})
public interface BriarIntegrationTestComponent
        extends BrambleIntegrationTestComponent {

    void inject(BriarIntegrationTest<BriarIntegrationTestComponent> init);

    void inject(AutoDeleteModule.EagerSingletons init);

    void inject(AvatarModule.EagerSingletons init);

    void inject(BlogModule.EagerSingletons init);

    void inject(ConversationModule.EagerSingletons init);

    void inject(ForumModule.EagerSingletons init);

    void inject(GroupInvitationModule.EagerSingletons init);

    void inject(IdentityModule.EagerSingletons init);

    void inject(IntroductionModule.EagerSingletons init);

    void inject(MessagingModule.EagerSingletons init);

    void inject(PrivateGroupModule.EagerSingletons init);

    void inject(SharingModule.EagerSingletons init);

    LifecycleManager getLifecycleManager();

    AttachmentReader getAttachmentReader();

    AvatarManager getAvatarManager();

    ContactManager getContactManager();

    ConversationManager getConversationManager();

    DatabaseComponent getDatabaseComponent();

    BlogManager getBlogManager();

    BlogSharingManager getBlogSharingManager();

    ForumSharingManager getForumSharingManager();

    ForumManager getForumManager();

    GroupInvitationManager getGroupInvitationManager();

    GroupInvitationFactory getGroupInvitationFactory();

    IntroductionManager getIntroductionManager();

    MessageTracker getMessageTracker();

    MessagingManager getMessagingManager();

    PrivateGroupManager getPrivateGroupManager();

    PrivateMessageFactory getPrivateMessageFactory();

    TransportPropertyManager getTransportPropertyManager();

    AuthorFactory getAuthorFactory();

    BlogFactory getBlogFactory();

    AutoDeleteManager getAutoDeleteManager();

    Clock getClock();

    TimeTravel getTimeTravel();

    class Helper {

        public static void injectEagerSingletons(
                BriarIntegrationTestComponent c) {
            BrambleCoreIntegrationTestEagerSingletons.Helper
                    .injectEagerSingletons(c);
            c.inject(new AutoDeleteModule.EagerSingletons());
            c.inject(new AvatarModule.EagerSingletons());
            c.inject(new BlogModule.EagerSingletons());
            c.inject(new ConversationModule.EagerSingletons());
            c.inject(new ForumModule.EagerSingletons());
            c.inject(new GroupInvitationModule.EagerSingletons());
            c.inject(new IdentityModule.EagerSingletons());
            c.inject(new IntroductionModule.EagerSingletons());
            c.inject(new MessagingModule.EagerSingletons());
            c.inject(new PrivateGroupModule.EagerSingletons());
            c.inject(new SharingModule.EagerSingletons());
        }
    }
}
