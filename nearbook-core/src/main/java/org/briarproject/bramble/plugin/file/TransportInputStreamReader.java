package org.briarproject.bramble.plugin.file;

import static org.briarproject.bramble.util.IoUtils.tryToClose;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;

import org.briarproject.bramble.api.plugin.TransportConnectionReader;
import org.briarproject.nullsafety.NotNullByDefault;

import java.io.InputStream;
import java.util.logging.Logger;

@NotNullByDefault
class TransportInputStreamReader implements TransportConnectionReader {

    private static final Logger LOG =
            getLogger(TransportInputStreamReader.class.getName());

    private final InputStream in;

    TransportInputStreamReader(InputStream in) {
        this.in = in;
    }

    @Override
    public InputStream getInputStream() {
        return in;
    }

    @Override
    public void dispose(boolean exception, boolean recognised) {
        tryToClose(in, LOG, WARNING);
    }
}
