package org.briarproject.masterproject.android.forum;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ForumModule {

    @Binds
    @IntoMap
    @ViewModelKey(ForumListViewModel.class)
    ViewModel bindForumListViewModel(ForumListViewModel forumListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForumViewModel.class)
    ViewModel bindForumViewModel(ForumViewModel forumViewModel);

}
