package org.briarproject.masterproject.android.privategroup.list;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class GroupListModule {

    @Binds
    @IntoMap
    @ViewModelKey(GroupListViewModel.class)
    abstract ViewModel bindGroupListViewModel(
            GroupListViewModel groupListViewModel);
}
