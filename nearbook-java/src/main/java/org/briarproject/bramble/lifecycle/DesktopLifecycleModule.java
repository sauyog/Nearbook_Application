package org.briarproject.bramble.lifecycle;

import static org.briarproject.bramble.util.OsUtils.isWindows;

import org.briarproject.bramble.api.lifecycle.ShutdownManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DesktopLifecycleModule extends LifecycleModule {

    @Provides
    @Singleton
    ShutdownManager provideDesktopShutdownManager() {
        if (isWindows()) return new WindowsShutdownManagerImpl();
        else return new ShutdownManagerImpl();
    }
}
