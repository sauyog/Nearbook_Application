package org.briarproject.briar.privategroup.invitation;

import static org.briarproject.bramble.api.sync.Group.Visibility.INVISIBLE;
import static org.briarproject.bramble.api.sync.Group.Visibility.SHARED;
import static org.briarproject.bramble.api.sync.Group.Visibility.VISIBLE;

import org.briarproject.bramble.api.FormatException;
import org.briarproject.bramble.api.sync.Group.Visibility;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
enum InviteeState implements State {

    START(0, INVISIBLE),
    INVITED(1, INVISIBLE),
    ACCEPTED(2, VISIBLE),
    JOINED(3, SHARED),
    LEFT(4, INVISIBLE),
    DISSOLVED(5, INVISIBLE),
    ERROR(6, INVISIBLE);

    private final int value;
    private final Visibility visibility;

    InviteeState(int value, Visibility visibility) {
        this.value = value;
        this.visibility = visibility;
    }

    static InviteeState fromValue(int value) throws FormatException {
        for (InviteeState s : values()) if (s.value == value) return s;
        throw new FormatException();
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public boolean isAwaitingResponse() {
        return this == INVITED;
    }
}
