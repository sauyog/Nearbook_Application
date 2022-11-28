package org.briarproject.masterproject.android.conversation;

import org.briarproject.masterproject.android.activity.ActivityScope;
import org.briarproject.masterproject.android.conversation.glide.BriarDataFetcherFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ConversationModule {

    @ActivityScope
    @Provides
    BriarDataFetcherFactory provideBriarDataFetcherFactory(
            BriarDataFetcherFactory dataFetcherFactory) {
        return dataFetcherFactory;
    }

}
