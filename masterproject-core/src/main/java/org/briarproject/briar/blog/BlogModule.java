package org.briarproject.briar.blog;

import org.briarproject.bramble.api.FeatureFlags;
import org.briarproject.bramble.api.client.ClientHelper;
import org.briarproject.bramble.api.contact.ContactManager;
import org.briarproject.bramble.api.data.MetadataEncoder;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.bramble.api.sync.GroupFactory;
import org.briarproject.bramble.api.sync.MessageFactory;
import org.briarproject.bramble.api.sync.validation.ValidationManager;
import org.briarproject.bramble.api.system.Clock;
import org.briarproject.masterproject.api.blog.BlogFactory;
import org.briarproject.masterproject.api.blog.BlogManager;
import org.briarproject.masterproject.api.blog.BlogPostFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.briarproject.masterproject.api.blog.BlogManager.CLIENT_ID;
import static org.briarproject.masterproject.api.blog.BlogManager.MAJOR_VERSION;

@Module
public class BlogModule {

	public static class EagerSingletons {
		@Inject
		BlogPostValidator blogPostValidator;
		@Inject
		BlogManager blogManager;
	}

	@Provides
	@Singleton
	BlogManager provideBlogManager(BlogManagerImpl blogManager,
			LifecycleManager lifecycleManager, ContactManager contactManager,
			ValidationManager validationManager, FeatureFlags featureFlags) {
		if (!featureFlags.shouldEnableBlogsInCore()) {
			return blogManager;
		}
		lifecycleManager.registerOpenDatabaseHook(blogManager);
		contactManager.registerContactHook(blogManager);
		validationManager.registerIncomingMessageHook(CLIENT_ID, MAJOR_VERSION,
				blogManager);
		return blogManager;
	}

	@Provides
	BlogPostFactory provideBlogPostFactory(
			BlogPostFactoryImpl blogPostFactory) {
		return blogPostFactory;
	}

	@Provides
	BlogFactory provideBlogFactory(BlogFactoryImpl blogFactory) {
		return blogFactory;
	}

	@Provides
	@Singleton
	BlogPostValidator provideBlogPostValidator(
			ValidationManager validationManager, GroupFactory groupFactory,
			MessageFactory messageFactory, BlogFactory blogFactory,
			ClientHelper clientHelper, MetadataEncoder metadataEncoder,
			Clock clock, FeatureFlags featureFlags) {
		BlogPostValidator validator = new BlogPostValidator(groupFactory,
				messageFactory, blogFactory, clientHelper, metadataEncoder,
				clock);
		if (featureFlags.shouldEnableBlogsInCore()) {
			validationManager.registerMessageValidator(CLIENT_ID, MAJOR_VERSION,
					validator);
		}
		return validator;
	}

}
