package org.briarproject.masterproject.android.logging;

import androidx.annotation.Nullable;

import org.briarproject.bramble.util.AndroidUtils;
import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface LogEncrypter {
    /**
     * Writes encrypted log records to {@link AndroidUtils#getLogcatFile}
     * and returns the encryption key if everything went fine.
     */
    @Nullable
    byte[] encryptLogs();
}
