package org.briarproject.masterproject.android.contact.add.nearby;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AddNearbyContactModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddNearbyContactViewModel.class)
    abstract ViewModel bindContactExchangeViewModel(
            AddNearbyContactViewModel addNearbyContactViewModel);

}
