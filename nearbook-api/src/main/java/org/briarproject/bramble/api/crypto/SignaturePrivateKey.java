package org.briarproject.bramble.api.crypto;

import static org.briarproject.bramble.api.crypto.CryptoConstants.KEY_TYPE_SIGNATURE;

import org.briarproject.bramble.api.Bytes;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * Type-safe wrapper for a public key used for signing.
 */
@Immutable
@NotNullByDefault
public class SignaturePrivateKey extends Bytes implements PrivateKey {

    public SignaturePrivateKey(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getKeyType() {
        return KEY_TYPE_SIGNATURE;
    }

    @Override
    public byte[] getEncoded() {
        return getBytes();
    }
}
