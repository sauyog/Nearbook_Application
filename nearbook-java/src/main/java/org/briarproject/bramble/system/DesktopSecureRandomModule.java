package org.briarproject.bramble.system;

import static org.briarproject.bramble.util.OsUtils.isLinux;
import static org.briarproject.bramble.util.OsUtils.isMac;

import org.briarproject.bramble.api.system.SecureRandomProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DesktopSecureRandomModule {

    @Provides
    @Singleton
    SecureRandomProvider provideSecureRandomProvider() {
        if (isLinux() || isMac()) return new UnixSecureRandomProvider();
        return () -> null; // Use system default
    }
}
