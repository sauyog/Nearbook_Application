package org.briarproject.masterproject.android.activity;

import static org.briarproject.masterproject.android.BriarService.BriarServiceConnection;

import android.app.Activity;

import org.briarproject.masterproject.android.controller.BriarController;
import org.briarproject.masterproject.android.controller.BriarControllerImpl;
import org.briarproject.masterproject.android.controller.DbController;
import org.briarproject.masterproject.android.controller.DbControllerImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    BaseActivity provideBaseActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    Activity provideActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    protected BriarController provideBriarController(
            BriarControllerImpl briarController) {
        activity.addLifecycleController(briarController);
        return briarController;
    }

    @ActivityScope
    @Provides
    DbController provideDBController(DbControllerImpl dbController) {
        return dbController;
    }

    @ActivityScope
    @Provides
    BriarServiceConnection provideBriarServiceConnection() {
        return new BriarServiceConnection();
    }
}
