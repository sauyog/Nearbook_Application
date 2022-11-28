package org.briarproject.masterproject.android;

import org.briarproject.bramble.BrambleAndroidEagerSingletons;
import org.briarproject.bramble.BrambleAndroidModule;
import org.briarproject.bramble.BrambleAppComponent;
import org.briarproject.bramble.BrambleCoreEagerSingletons;
import org.briarproject.bramble.BrambleCoreModule;
import org.briarproject.bramble.account.BriarAccountModule;
import org.briarproject.bramble.api.FeatureFlags;
import org.briarproject.bramble.api.account.AccountManager;
import org.briarproject.bramble.api.connection.ConnectionRegistry;
import org.briarproject.bramble.api.contact.ContactExchangeManager;
import org.briarproject.bramble.api.contact.ContactManager;
import org.briarproject.bramble.api.crypto.CryptoExecutor;
import org.briarproject.bramble.api.crypto.PasswordStrengthEstimator;
import org.briarproject.bramble.api.db.DatabaseExecutor;
import org.briarproject.bramble.api.db.TransactionManager;
import org.briarproject.bramble.api.event.EventBus;
import org.briarproject.bramble.api.identity.IdentityManager;
import org.briarproject.bramble.api.keyagreement.KeyAgreementTask;
import org.briarproject.bramble.api.keyagreement.PayloadEncoder;
import org.briarproject.bramble.api.keyagreement.PayloadParser;
import org.briarproject.bramble.api.lifecycle.IoExecutor;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.bramble.api.plugin.PluginManager;
import org.briarproject.bramble.api.settings.SettingsManager;
import org.briarproject.bramble.api.system.AndroidExecutor;
import org.briarproject.bramble.api.system.AndroidWakeLockManager;
import org.briarproject.bramble.api.system.Clock;
import org.briarproject.bramble.api.system.LocationUtils;
import org.briarproject.bramble.mailbox.ModularMailboxModule;
import org.briarproject.bramble.plugin.file.RemovableDriveModule;
import org.briarproject.bramble.plugin.tor.CircumventionProvider;
import org.briarproject.bramble.system.ClockModule;
import org.briarproject.briar.BriarCoreEagerSingletons;
import org.briarproject.briar.BriarCoreModule;
import org.briarproject.masterproject.android.attachment.AttachmentModule;
import org.briarproject.masterproject.android.attachment.media.MediaModule;
import org.briarproject.masterproject.android.contact.connect.BluetoothIntroFragment;
import org.briarproject.masterproject.android.conversation.glide.BriarModelLoader;
import org.briarproject.masterproject.android.hotspot.AbstractTabsFragment;
import org.briarproject.masterproject.android.hotspot.FallbackFragment;
import org.briarproject.masterproject.android.hotspot.HotspotIntroFragment;
import org.briarproject.masterproject.android.hotspot.ManualHotspotFragment;
import org.briarproject.masterproject.android.hotspot.QrHotspotFragment;
import org.briarproject.masterproject.android.logging.CachingLogHandler;
import org.briarproject.masterproject.android.login.SignInReminderReceiver;
import org.briarproject.masterproject.android.mailbox.ErrorFragment;
import org.briarproject.masterproject.android.mailbox.ErrorWizardFragment;
import org.briarproject.masterproject.android.mailbox.MailboxScanFragment;
import org.briarproject.masterproject.android.mailbox.MailboxStatusFragment;
import org.briarproject.masterproject.android.mailbox.OfflineFragment;
import org.briarproject.masterproject.android.mailbox.SetupDownloadFragment;
import org.briarproject.masterproject.android.mailbox.SetupIntroFragment;
import org.briarproject.masterproject.android.removabledrive.ChooserFragment;
import org.briarproject.masterproject.android.removabledrive.ReceiveFragment;
import org.briarproject.masterproject.android.removabledrive.SendFragment;
import org.briarproject.masterproject.android.settings.ConnectionsFragment;
import org.briarproject.masterproject.android.settings.NotificationsFragment;
import org.briarproject.masterproject.android.settings.SecurityFragment;
import org.briarproject.masterproject.android.settings.SettingsFragment;
import org.briarproject.masterproject.android.view.EmojiTextInputView;
import org.briarproject.masterproject.api.android.AndroidNotificationManager;
import org.briarproject.masterproject.api.android.DozeWatchdog;
import org.briarproject.masterproject.api.android.LockManager;
import org.briarproject.masterproject.api.android.ScreenFilterMonitor;
import org.briarproject.masterproject.api.attachment.AttachmentReader;
import org.briarproject.masterproject.api.autodelete.AutoDeleteManager;
import org.briarproject.masterproject.api.blog.BlogManager;
import org.briarproject.masterproject.api.blog.BlogPostFactory;
import org.briarproject.masterproject.api.blog.BlogSharingManager;
import org.briarproject.masterproject.api.client.MessageTracker;
import org.briarproject.masterproject.api.conversation.ConversationManager;
import org.briarproject.masterproject.api.feed.FeedManager;
import org.briarproject.masterproject.api.forum.ForumManager;
import org.briarproject.masterproject.api.forum.ForumSharingManager;
import org.briarproject.masterproject.api.identity.AuthorManager;
import org.briarproject.masterproject.api.introduction.IntroductionManager;
import org.briarproject.masterproject.api.messaging.MessagingManager;
import org.briarproject.masterproject.api.messaging.PrivateMessageFactory;
import org.briarproject.masterproject.api.privategroup.GroupMessageFactory;
import org.briarproject.masterproject.api.privategroup.PrivateGroupFactory;
import org.briarproject.masterproject.api.privategroup.PrivateGroupManager;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationFactory;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationManager;
import org.briarproject.masterproject.api.test.TestDataCreator;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProvider;
import dagger.Component;

@Singleton
@Component(modules = {
		BrambleCoreModule.class,
		BriarCoreModule.class,
		BrambleAndroidModule.class,
		BriarAccountModule.class,
		AppModule.class,
		AttachmentModule.class,
		ClockModule.class,
		MediaModule.class,
		ModularMailboxModule.class,
		RemovableDriveModule.class
})
public interface AndroidComponent
		extends BrambleCoreEagerSingletons, BrambleAndroidEagerSingletons,
		BriarCoreEagerSingletons, AndroidEagerSingletons, BrambleAppComponent {

	// Exposed objects
	@CryptoExecutor
	Executor cryptoExecutor();

	PasswordStrengthEstimator passwordStrengthIndicator();

	@DatabaseExecutor
	Executor databaseExecutor();

	TransactionManager transactionManager();

	MessageTracker messageTracker();

	LifecycleManager lifecycleManager();

	IdentityManager identityManager();

	AttachmentReader attachmentReader();

	AuthorManager authorManager();

	PluginManager pluginManager();

	EventBus eventBus();

	AndroidNotificationManager androidNotificationManager();

	ScreenFilterMonitor screenFilterMonitor();

	ConnectionRegistry connectionRegistry();

	ContactManager contactManager();

	ConversationManager conversationManager();

	MessagingManager messagingManager();

	PrivateMessageFactory privateMessageFactory();

	PrivateGroupManager privateGroupManager();

	GroupInvitationFactory groupInvitationFactory();

	GroupInvitationManager groupInvitationManager();

	PrivateGroupFactory privateGroupFactory();

	GroupMessageFactory groupMessageFactory();

	ForumManager forumManager();

	ForumSharingManager forumSharingManager();

	BlogSharingManager blogSharingManager();

	BlogManager blogManager();

	BlogPostFactory blogPostFactory();

	SettingsManager settingsManager();

	ContactExchangeManager contactExchangeManager();

	KeyAgreementTask keyAgreementTask();

	PayloadEncoder payloadEncoder();

	PayloadParser payloadParser();

	IntroductionManager introductionManager();

	AndroidExecutor androidExecutor();

	FeedManager feedManager();

	Clock clock();

	TestDataCreator testDataCreator();

	DozeWatchdog dozeWatchdog();

	@IoExecutor
	Executor ioExecutor();

	AccountManager accountManager();

	LockManager lockManager();

	LocationUtils locationUtils();

	CircumventionProvider circumventionProvider();

	ViewModelProvider.Factory viewModelFactory();

	FeatureFlags featureFlags();

	AndroidWakeLockManager wakeLockManager();

	CachingLogHandler logHandler();

	Thread.UncaughtExceptionHandler exceptionHandler();

	AutoDeleteManager autoDeleteManager();

	void inject(SignInReminderReceiver briarService);

	void inject(BriarService briarService);

	void inject(NotificationCleanupService notificationCleanupService);

	void inject(EmojiTextInputView textInputView);

	void inject(BriarModelLoader briarModelLoader);

	void inject(SettingsFragment settingsFragment);

	void inject(ConnectionsFragment connectionsFragment);

	void inject(SecurityFragment securityFragment);

	void inject(NotificationsFragment notificationsFragment);

	void inject(HotspotIntroFragment hotspotIntroFragment);

	void inject(AbstractTabsFragment abstractTabsFragment);

	void inject(QrHotspotFragment qrHotspotFragment);

	void inject(ManualHotspotFragment manualHotspotFragment);

	void inject(FallbackFragment fallbackFragment);

	void inject(ChooserFragment chooserFragment);

	void inject(SendFragment sendFragment);

	void inject(ReceiveFragment receiveFragment);

	void inject(BluetoothIntroFragment bluetoothIntroFragment);

	void inject(SetupIntroFragment setupIntroFragment);

	void inject(SetupDownloadFragment setupDownloadFragment);

	void inject(MailboxScanFragment mailboxScanFragment);

	void inject(OfflineFragment offlineFragment);

	void inject(ErrorFragment errorFragment);

	void inject(MailboxStatusFragment mailboxStatusFragment);

	void inject(ErrorWizardFragment errorWizardFragment);
}
