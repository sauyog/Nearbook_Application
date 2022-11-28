package org.briarproject.briar.identity;

import org.briarproject.masterproject.api.identity.AuthorManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdentityModule {

    @Provides
    @Singleton
    AuthorManager provideAuthorManager(AuthorManagerImpl authorManager) {
        return authorManager;
    }

    public static class EagerSingletons {
        @Inject
        AuthorManager authorManager;
    }

}
