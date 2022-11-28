package org.briarproject.masterproject.android.login;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartupViewModel.class)
    abstract ViewModel bindStartupViewModel(StartupViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel.class)
    abstract ViewModel bindChangePasswordViewModel(
            ChangePasswordViewModel viewModel);
}
