package org.briarproject.masterproject.api.introduction;

import org.briarproject.bramble.api.FormatException;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public enum Role {

    INTRODUCER(0), INTRODUCEE(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public static Role fromValue(int value) throws FormatException {
        for (Role r : values()) if (r.value == value) return r;
        throw new FormatException();
    }

    public int getValue() {
        return value;
    }

}
