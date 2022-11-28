package org.briarproject.briar.util;

import static org.briarproject.bramble.util.ValidationUtils.checkRange;
import static org.briarproject.masterproject.api.autodelete.AutoDeleteConstants.MAX_AUTO_DELETE_TIMER_MS;
import static org.briarproject.masterproject.api.autodelete.AutoDeleteConstants.MIN_AUTO_DELETE_TIMER_MS;
import static org.briarproject.masterproject.api.autodelete.AutoDeleteConstants.NO_AUTO_DELETE_TIMER;

import org.briarproject.bramble.api.FormatException;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ValidationUtils {

    public static long validateAutoDeleteTimer(@Nullable Long timer)
            throws FormatException {
        if (timer == null) return NO_AUTO_DELETE_TIMER;
        checkRange(timer, MIN_AUTO_DELETE_TIMER_MS, MAX_AUTO_DELETE_TIMER_MS);
        return timer;
    }
}
