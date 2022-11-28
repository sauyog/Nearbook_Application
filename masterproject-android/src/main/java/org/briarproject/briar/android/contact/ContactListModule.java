package org.briarproject.masterproject.android.contact;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactListModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel.class)
    abstract ViewModel bindContactListViewModel(
            ContactListViewModel contactListViewModel);

}
