package org.briarproject.briar.feed;

import org.briarproject.bramble.BrambleCoreIntegrationTestEagerSingletons;
import org.briarproject.bramble.BrambleCoreModule;
import org.briarproject.bramble.api.identity.IdentityManager;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.bramble.mailbox.ModularMailboxModule;
import org.briarproject.bramble.test.BrambleCoreIntegrationTestModule;
import org.briarproject.bramble.test.TestDnsModule;
import org.briarproject.bramble.test.TestPluginConfigModule;
import org.briarproject.bramble.test.TestSocksModule;
import org.briarproject.masterproject.api.blog.BlogManager;
import org.briarproject.masterproject.api.feed.FeedManager;
import org.briarproject.briar.avatar.AvatarModule;
import org.briarproject.briar.blog.BlogModule;
import org.briarproject.briar.client.BriarClientModule;
import org.briarproject.briar.identity.IdentityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		BrambleCoreIntegrationTestModule.class,
		BrambleCoreModule.class,
		AvatarModule.class,
		BlogModule.class,
		BriarClientModule.class,
		FeedModule.class,
		IdentityModule.class,
		ModularMailboxModule.class,
		TestDnsModule.class,
		TestSocksModule.class,
		TestPluginConfigModule.class,
})
interface FeedManagerIntegrationTestComponent
		extends BrambleCoreIntegrationTestEagerSingletons {

	void inject(FeedManagerIntegrationTest testCase);

	void inject(AvatarModule.EagerSingletons init);

	void inject(BlogModule.EagerSingletons init);

	void inject(FeedModule.EagerSingletons init);

	IdentityManager getIdentityManager();

	LifecycleManager getLifecycleManager();

	FeedManager getFeedManager();

	BlogManager getBlogManager();

	class Helper {

		public static void injectEagerSingletons(
				FeedManagerIntegrationTestComponent c) {
			BrambleCoreIntegrationTestEagerSingletons.Helper
					.injectEagerSingletons(c);
			c.inject(new AvatarModule.EagerSingletons());
			c.inject(new BlogModule.EagerSingletons());
			c.inject(new FeedModule.EagerSingletons());
		}
	}
}
