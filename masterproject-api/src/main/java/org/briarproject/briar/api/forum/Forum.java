package org.briarproject.masterproject.api.forum;

import org.briarproject.bramble.api.sync.Group;
import org.briarproject.masterproject.api.client.NamedGroup;
import org.briarproject.masterproject.api.sharing.Shareable;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class Forum extends NamedGroup implements Shareable {

    public Forum(Group group, String name, byte[] salt) {
        super(group, name, salt);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Forum && super.equals(o);
    }

}
