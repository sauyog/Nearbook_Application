package org.briarproject.bramble.api.plugin.duplex;

import org.briarproject.bramble.api.plugin.TransportConnectionReader;
import org.briarproject.bramble.api.plugin.TransportConnectionWriter;
import org.briarproject.bramble.api.properties.TransportProperties;
import org.briarproject.nullsafety.NotNullByDefault;

/**
 * An interface for reading and writing data over a duplex transport. The
 * connection is not responsible for encrypting/decrypting or authenticating
 * the data.
 */
@NotNullByDefault
public interface DuplexTransportConnection {

	/**
	 * Returns a {@link TransportConnectionReader TransportConnectionReader}
	 * for reading from the connection.
	 */
	TransportConnectionReader getReader();

	/**
	 * Returns a {@link TransportConnectionWriter TransportConnectionWriter}
	 * for writing to the connection.
	 */
	TransportConnectionWriter getWriter();

	/**
	 * Returns a possibly empty set of {@link TransportProperties} describing
	 * the remote peer.
	 */
	TransportProperties getRemoteProperties();
}
