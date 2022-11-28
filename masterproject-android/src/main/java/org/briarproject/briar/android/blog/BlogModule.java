package org.briarproject.masterproject.android.blog;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface BlogModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel.class)
    ViewModel bindFeedViewModel(FeedViewModel feedViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel.class)
    ViewModel bindBlogViewModel(BlogViewModel blogViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RssFeedViewModel.class)
    ViewModel bindRssFeedViewModel(RssFeedViewModel rssFeedViewModel);
}
