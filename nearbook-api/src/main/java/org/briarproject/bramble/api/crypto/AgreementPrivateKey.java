package org.briarproject.bramble.api.crypto;

import static org.briarproject.bramble.api.crypto.CryptoConstants.KEY_TYPE_AGREEMENT;

import org.briarproject.bramble.api.Bytes;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

/**
 * Type-safe wrapper for a private key used for key agreement.
 */
@Immutable
@NotNullByDefault
public class AgreementPrivateKey extends Bytes implements PrivateKey {

    public AgreementPrivateKey(byte[] encoded) {
        super(encoded);
    }

    @Override
    public String getKeyType() {
        return KEY_TYPE_AGREEMENT;
    }

    @Override
    public byte[] getEncoded() {
        return getBytes();
    }
}
