package org.briarproject.masterproject.android.settings;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SettingsModule {

	@Binds
	@IntoMap
	@ViewModelKey(SettingsViewModel.class)
	abstract ViewModel bindSettingsViewModel(
			SettingsViewModel settingsViewModel);

}
