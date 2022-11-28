package org.briarproject.masterproject.android.logging;

import androidx.annotation.Nullable;

import org.briarproject.bramble.util.AndroidUtils;
import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface LogDecrypter {
    /**
     * Returns decrypted log records from {@link AndroidUtils#getLogcatFile}
     * or null if there was an error reading the logs.
     */
    @Nullable
    String decryptLogs(@Nullable byte[] logKey);
}
