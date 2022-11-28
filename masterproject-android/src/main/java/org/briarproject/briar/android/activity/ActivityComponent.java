package org.briarproject.masterproject.android.activity;

import android.app.Activity;

import org.briarproject.masterproject.android.AndroidComponent;
import org.briarproject.masterproject.android.StartupFailureActivity;
import org.briarproject.masterproject.android.account.SetupActivity;
import org.briarproject.masterproject.android.account.SetupFragment;
import org.briarproject.masterproject.android.account.UnlockActivity;
import org.briarproject.masterproject.android.blog.BlogActivity;
import org.briarproject.masterproject.android.blog.BlogFragment;
import org.briarproject.masterproject.android.blog.BlogPostFragment;
import org.briarproject.masterproject.android.blog.FeedFragment;
import org.briarproject.masterproject.android.blog.ReblogActivity;
import org.briarproject.masterproject.android.blog.ReblogFragment;
import org.briarproject.masterproject.android.blog.RssFeedActivity;
import org.briarproject.masterproject.android.blog.RssFeedDeleteFeedDialogFragment;
import org.briarproject.masterproject.android.blog.RssFeedImportFailedDialogFragment;
import org.briarproject.masterproject.android.blog.RssFeedImportFragment;
import org.briarproject.masterproject.android.blog.RssFeedManageFragment;
import org.briarproject.masterproject.android.blog.WriteBlogPostActivity;
import org.briarproject.masterproject.android.contact.ContactListFragment;
import org.briarproject.masterproject.android.contact.add.nearby.AddNearbyContactActivity;
import org.briarproject.masterproject.android.contact.add.nearby.AddNearbyContactErrorFragment;
import org.briarproject.masterproject.android.contact.add.nearby.AddNearbyContactFragment;
import org.briarproject.masterproject.android.contact.add.nearby.AddNearbyContactIntroFragment;
import org.briarproject.masterproject.android.contact.add.remote.AddContactActivity;
import org.briarproject.masterproject.android.contact.add.remote.LinkExchangeFragment;
import org.briarproject.masterproject.android.contact.add.remote.NicknameFragment;
import org.briarproject.masterproject.android.contact.add.remote.PendingContactListActivity;
import org.briarproject.masterproject.android.contact.connect.ConnectViaBluetoothActivity;
import org.briarproject.masterproject.android.conversation.AliasDialogFragment;
import org.briarproject.masterproject.android.conversation.ConversationActivity;
import org.briarproject.masterproject.android.conversation.ConversationSettingsDialog;
import org.briarproject.masterproject.android.conversation.ImageActivity;
import org.briarproject.masterproject.android.conversation.ImageFragment;
import org.briarproject.masterproject.android.forum.CreateForumActivity;
import org.briarproject.masterproject.android.forum.ForumActivity;
import org.briarproject.masterproject.android.forum.ForumListFragment;
import org.briarproject.masterproject.android.fragment.ScreenFilterDialogFragment;
import org.briarproject.masterproject.android.hotspot.HotspotActivity;
import org.briarproject.masterproject.android.introduction.ContactChooserFragment;
import org.briarproject.masterproject.android.introduction.IntroductionActivity;
import org.briarproject.masterproject.android.introduction.IntroductionMessageFragment;
import org.briarproject.masterproject.android.login.ChangePasswordActivity;
import org.briarproject.masterproject.android.login.OpenDatabaseFragment;
import org.briarproject.masterproject.android.login.PasswordFragment;
import org.briarproject.masterproject.android.login.StartupActivity;
import org.briarproject.masterproject.android.mailbox.MailboxActivity;
import org.briarproject.masterproject.android.navdrawer.NavDrawerActivity;
import org.briarproject.masterproject.android.navdrawer.TransportsActivity;
import org.briarproject.masterproject.android.panic.PanicPreferencesActivity;
import org.briarproject.masterproject.android.panic.PanicResponderActivity;
import org.briarproject.masterproject.android.privategroup.conversation.GroupActivity;
import org.briarproject.masterproject.android.privategroup.creation.CreateGroupActivity;
import org.briarproject.masterproject.android.privategroup.creation.CreateGroupFragment;
import org.briarproject.masterproject.android.privategroup.creation.CreateGroupModule;
import org.briarproject.masterproject.android.privategroup.creation.GroupInviteActivity;
import org.briarproject.masterproject.android.privategroup.creation.GroupInviteFragment;
import org.briarproject.masterproject.android.privategroup.invitation.GroupInvitationActivity;
import org.briarproject.masterproject.android.privategroup.invitation.GroupInvitationModule;
import org.briarproject.masterproject.android.privategroup.list.GroupListFragment;
import org.briarproject.masterproject.android.privategroup.memberlist.GroupMemberListActivity;
import org.briarproject.masterproject.android.privategroup.memberlist.GroupMemberModule;
import org.briarproject.masterproject.android.privategroup.reveal.GroupRevealModule;
import org.briarproject.masterproject.android.privategroup.reveal.RevealContactsActivity;
import org.briarproject.masterproject.android.privategroup.reveal.RevealContactsFragment;
import org.briarproject.masterproject.android.removabledrive.RemovableDriveActivity;
import org.briarproject.masterproject.android.reporting.CrashFragment;
import org.briarproject.masterproject.android.reporting.CrashReportActivity;
import org.briarproject.masterproject.android.reporting.ReportFormFragment;
import org.briarproject.masterproject.android.settings.ConfirmAvatarDialogFragment;
import org.briarproject.masterproject.android.settings.SettingsActivity;
import org.briarproject.masterproject.android.settings.SettingsFragment;
import org.briarproject.masterproject.android.sharing.BlogInvitationActivity;
import org.briarproject.masterproject.android.sharing.BlogSharingStatusActivity;
import org.briarproject.masterproject.android.sharing.ForumInvitationActivity;
import org.briarproject.masterproject.android.sharing.ForumSharingStatusActivity;
import org.briarproject.masterproject.android.sharing.ShareBlogActivity;
import org.briarproject.masterproject.android.sharing.ShareBlogFragment;
import org.briarproject.masterproject.android.sharing.ShareForumActivity;
import org.briarproject.masterproject.android.sharing.ShareForumFragment;
import org.briarproject.masterproject.android.sharing.SharingModule;
import org.briarproject.masterproject.android.splash.ExpiredOldAndroidActivity;
import org.briarproject.masterproject.android.splash.SplashScreenActivity;
import org.briarproject.masterproject.android.test.TestDataActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {
		ActivityModule.class,
		CreateGroupModule.class,
		GroupInvitationModule.class,
		GroupMemberModule.class,
		GroupRevealModule.class,
		SharingModule.SharingLegacyModule.class
}, dependencies = AndroidComponent.class)
public interface ActivityComponent {

	Activity activity();

	void inject(SplashScreenActivity activity);

	void inject(StartupActivity activity);

	void inject(SetupActivity activity);

	void inject(NavDrawerActivity activity);

	void inject(PanicResponderActivity activity);

	void inject(PanicPreferencesActivity activity);

	void inject(AddNearbyContactActivity activity);

	void inject(ConversationActivity activity);

	void inject(ImageActivity activity);

	void inject(ForumInvitationActivity activity);

	void inject(BlogInvitationActivity activity);

	void inject(CreateGroupActivity activity);

	void inject(GroupActivity activity);

	void inject(GroupInviteActivity activity);

	void inject(GroupInvitationActivity activity);

	void inject(GroupMemberListActivity activity);

	void inject(RevealContactsActivity activity);

	void inject(CreateForumActivity activity);

	void inject(ShareForumActivity activity);

	void inject(ShareBlogActivity activity);

	void inject(ForumSharingStatusActivity activity);

	void inject(BlogSharingStatusActivity activity);

	void inject(ForumActivity activity);

	void inject(BlogActivity activity);

	void inject(WriteBlogPostActivity activity);

	void inject(BlogFragment fragment);

	void inject(BlogPostFragment fragment);

	void inject(ReblogFragment fragment);

	void inject(ReblogActivity activity);

	void inject(SettingsActivity activity);

	void inject(TransportsActivity activity);

	void inject(TestDataActivity activity);

	void inject(ChangePasswordActivity activity);

	void inject(IntroductionActivity activity);

	void inject(RssFeedActivity activity);

	void inject(StartupFailureActivity activity);

	void inject(UnlockActivity activity);

	void inject(AddContactActivity activity);

	void inject(PendingContactListActivity activity);

	void inject(CrashReportActivity crashReportActivity);

	void inject(HotspotActivity hotspotActivity);

	void inject(RemovableDriveActivity activity);

	void inject(ExpiredOldAndroidActivity activity);

	// Fragments

	void inject(SetupFragment fragment);

	void inject(PasswordFragment imageFragment);

	void inject(OpenDatabaseFragment activity);

	void inject(ContactListFragment fragment);

	void inject(CreateGroupFragment fragment);

	void inject(GroupListFragment fragment);

	void inject(GroupInviteFragment fragment);

	void inject(RevealContactsFragment activity);

	void inject(ForumListFragment fragment);

	void inject(FeedFragment fragment);

	void inject(AddNearbyContactIntroFragment fragment);

	void inject(AddNearbyContactFragment fragment);

	void inject(LinkExchangeFragment fragment);

	void inject(NicknameFragment fragment);

	void inject(ContactChooserFragment fragment);

	void inject(ShareForumFragment fragment);

	void inject(ShareBlogFragment fragment);

	void inject(IntroductionMessageFragment fragment);

	void inject(SettingsFragment fragment);

	void inject(ScreenFilterDialogFragment fragment);

	void inject(AddNearbyContactErrorFragment fragment);

	void inject(AliasDialogFragment aliasDialogFragment);

	void inject(ImageFragment imageFragment);

	void inject(ReportFormFragment reportFormFragment);

	void inject(CrashFragment crashFragment);

	void inject(ConfirmAvatarDialogFragment fragment);

	void inject(ConversationSettingsDialog dialog);

	void inject(RssFeedImportFragment fragment);

	void inject(RssFeedManageFragment fragment);

	void inject(RssFeedImportFailedDialogFragment fragment);

	void inject(RssFeedDeleteFeedDialogFragment fragment);

	void inject(ConnectViaBluetoothActivity connectViaBluetoothActivity);

	void inject(MailboxActivity mailboxActivity);
}
