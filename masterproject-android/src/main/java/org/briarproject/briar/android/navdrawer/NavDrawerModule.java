package org.briarproject.masterproject.android.navdrawer;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NavDrawerModule {

    @Binds
    @IntoMap
    @ViewModelKey(NavDrawerViewModel.class)
    abstract ViewModel bindNavDrawerViewModel(
            NavDrawerViewModel navDrawerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PluginViewModel.class)
    abstract ViewModel bindPluginViewModel(PluginViewModel pluginViewModel);
}
