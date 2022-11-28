package org.briarproject.masterproject.android.privategroup.creation;

import org.briarproject.masterproject.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateGroupModule {

    @ActivityScope
    @Provides
    CreateGroupController provideCreateGroupController(
            CreateGroupControllerImpl createGroupController) {
        return createGroupController;
    }

}
