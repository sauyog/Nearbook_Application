package org.briarproject.masterproject.android.privategroup.invitation;

import org.briarproject.masterproject.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupInvitationModule {

	@ActivityScope
	@Provides
	GroupInvitationController provideInvitationGroupController(
			GroupInvitationControllerImpl groupInvitationController) {
		return groupInvitationController;
	}
}
