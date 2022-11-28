package org.briarproject.bramble.plugin.file;

import static org.briarproject.bramble.api.mailbox.MailboxConstants.ID;
import static org.briarproject.bramble.api.mailbox.MailboxConstants.MAX_LATENCY;

import org.briarproject.bramble.api.plugin.PluginCallback;
import org.briarproject.bramble.api.plugin.TransportId;
import org.briarproject.bramble.api.plugin.simplex.SimplexPlugin;
import org.briarproject.bramble.api.plugin.simplex.SimplexPluginFactory;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.inject.Inject;

@NotNullByDefault
public class MailboxPluginFactory implements SimplexPluginFactory {

    @Inject
    MailboxPluginFactory() {
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
        return new MailboxPlugin(callback, MAX_LATENCY);
    }
}
