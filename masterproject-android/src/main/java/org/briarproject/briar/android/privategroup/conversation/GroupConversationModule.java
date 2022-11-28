package org.briarproject.masterproject.android.privategroup.conversation;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface GroupConversationModule {

    @Binds
    @IntoMap
    @ViewModelKey(GroupViewModel.class)
    ViewModel bindGroupViewModel(GroupViewModel groupViewModel);

}
