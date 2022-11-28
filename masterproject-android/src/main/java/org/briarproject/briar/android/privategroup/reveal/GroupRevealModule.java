package org.briarproject.masterproject.android.privategroup.reveal;

import org.briarproject.masterproject.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupRevealModule {

	@ActivityScope
	@Provides
	RevealContactsController provideRevealContactsController(
			RevealContactsControllerImpl revealContactsController) {
		return revealContactsController;
	}
}
