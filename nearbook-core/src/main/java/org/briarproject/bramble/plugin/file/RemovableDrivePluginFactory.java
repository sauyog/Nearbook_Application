package org.briarproject.bramble.plugin.file;

import static org.briarproject.bramble.api.plugin.file.RemovableDriveConstants.ID;
import static java.util.concurrent.TimeUnit.DAYS;

import org.briarproject.bramble.api.plugin.PluginCallback;
import org.briarproject.bramble.api.plugin.TransportId;
import org.briarproject.bramble.api.plugin.simplex.SimplexPlugin;
import org.briarproject.bramble.api.plugin.simplex.SimplexPluginFactory;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
public class RemovableDrivePluginFactory implements SimplexPluginFactory {

    static final long MAX_LATENCY = DAYS.toMillis(28);

    @Inject
    RemovableDrivePluginFactory() {
    }

    @Override
    public TransportId getId() {
        return ID;
    }

    @Override
    public long getMaxLatency() {
        return MAX_LATENCY;
    }

    @Nullable
    @Override
    public SimplexPlugin createPlugin(PluginCallback callback) {
        return new RemovableDrivePlugin(callback, MAX_LATENCY);
    }
}
