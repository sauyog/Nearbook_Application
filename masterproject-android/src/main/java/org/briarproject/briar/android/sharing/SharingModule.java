package org.briarproject.masterproject.android.sharing;

import org.briarproject.masterproject.android.activity.ActivityScope;
import org.briarproject.masterproject.android.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class SharingModule {

    @Provides
    SharingController provideSharingController(
            SharingControllerImpl sharingController) {
        return sharingController;
    }

    @Module
    @Deprecated
    public static class SharingLegacyModule {

        @ActivityScope
        @Provides
        ShareForumController provideShareForumController(
                ShareForumControllerImpl shareForumController) {
            return shareForumController;
        }

        @ActivityScope
        @Provides
        BlogInvitationController provideInvitationBlogController(
                BaseActivity activity,
                BlogInvitationControllerImpl blogInvitationController) {
            activity.addLifecycleController(blogInvitationController);
            return blogInvitationController;
        }

        @ActivityScope
        @Provides
        ForumInvitationController provideInvitationForumController(
                BaseActivity activity,
                ForumInvitationControllerImpl forumInvitationController) {
            activity.addLifecycleController(forumInvitationController);
            return forumInvitationController;
        }

        @ActivityScope
        @Provides
        ShareBlogController provideShareBlogController(
                ShareBlogControllerImpl shareBlogController) {
            return shareBlogController;
        }
    }

}
