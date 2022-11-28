package org.briarproject.masterproject.android.privategroup.memberlist;

import org.briarproject.masterproject.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupMemberModule {

    @ActivityScope
    @Provides
    GroupMemberListController provideGroupMemberListController(
            GroupMemberListControllerImpl groupMemberListController) {
        return groupMemberListController;
    }
}
