package org.briarproject.briar.autodelete;

import org.briarproject.bramble.api.contact.ContactManager;
import org.briarproject.bramble.api.lifecycle.LifecycleManager;
import org.briarproject.masterproject.api.autodelete.AutoDeleteManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AutoDeleteModule {

    @Provides
    @Singleton
    AutoDeleteManager provideAutoDeleteManager(
            LifecycleManager lifecycleManager, ContactManager contactManager,
            AutoDeleteManagerImpl autoDeleteManager) {
        lifecycleManager.registerOpenDatabaseHook(autoDeleteManager);
        contactManager.registerContactHook(autoDeleteManager);
        // Don't need to register with the client versioning manager as this
        // client's groups aren't shared with contacts
        return autoDeleteManager;
    }

    public static class EagerSingletons {
        @Inject
        AutoDeleteManager autoDeleteManager;
    }
}
