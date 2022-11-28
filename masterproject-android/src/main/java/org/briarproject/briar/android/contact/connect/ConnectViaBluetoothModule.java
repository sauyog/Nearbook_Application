package org.briarproject.masterproject.android.contact.connect;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ConnectViaBluetoothModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConnectViaBluetoothViewModel.class)
    abstract ViewModel bindContactListViewModel(
            ConnectViaBluetoothViewModel connectViaBluetoothViewModel);

}
