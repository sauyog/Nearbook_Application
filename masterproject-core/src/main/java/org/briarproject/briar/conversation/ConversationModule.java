package org.briarproject.briar.conversation;

import org.briarproject.masterproject.api.conversation.ConversationManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ConversationModule {

    @Provides
    @Singleton
    ConversationManager provideConversationManager(
            ConversationManagerImpl conversationManager) {
        return conversationManager;
    }

    public static class EagerSingletons {
        @Inject
        ConversationManager conversationManager;
    }
}
