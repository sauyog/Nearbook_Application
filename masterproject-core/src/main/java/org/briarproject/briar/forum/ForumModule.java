package org.briarproject.briar.forum;

import static org.briarproject.masterproject.api.forum.ForumManager.CLIENT_ID;
import static org.briarproject.masterproject.api.forum.ForumManager.MAJOR_VERSION;

import org.briarproject.bramble.api.FeatureFlags;
import org.briarproject.bramble.api.client.ClientHelper;
import org.briarproject.bramble.api.data.MetadataEncoder;
import org.briarproject.bramble.api.sync.validation.ValidationManager;
import org.briarproject.bramble.api.system.Clock;
import org.briarproject.masterproject.api.forum.ForumFactory;
import org.briarproject.masterproject.api.forum.ForumManager;
import org.briarproject.masterproject.api.forum.ForumPostFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ForumModule {

    @Provides
    @Singleton
    ForumManager provideForumManager(ForumManagerImpl forumManager,
                                     ValidationManager validationManager,
                                     FeatureFlags featureFlags) {
        if (!featureFlags.shouldEnableForumsInCore()) {
            return forumManager;
        }
        validationManager.registerIncomingMessageHook(CLIENT_ID, MAJOR_VERSION,
                forumManager);
        return forumManager;
    }

    @Provides
    ForumPostFactory provideForumPostFactory(
            ForumPostFactoryImpl forumPostFactory) {
        return forumPostFactory;
    }

    @Provides
    ForumFactory provideForumFactory(ForumFactoryImpl forumFactory) {
        return forumFactory;
    }

    @Provides
    @Singleton
    ForumPostValidator provideForumPostValidator(
            ValidationManager validationManager, ClientHelper clientHelper,
            MetadataEncoder metadataEncoder, Clock clock,
            FeatureFlags featureFlags) {
        ForumPostValidator validator = new ForumPostValidator(clientHelper,
                metadataEncoder, clock);
        if (featureFlags.shouldEnableForumsInCore()) {
            validationManager.registerMessageValidator(CLIENT_ID, MAJOR_VERSION,
                    validator);
        }
        return validator;
    }

    public static class EagerSingletons {
        @Inject
        ForumManager forumManager;
        @Inject
        ForumPostValidator forumPostValidator;
    }

}
