package org.briarproject.masterproject.api.privategroup;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.bramble.api.sync.Group;
import org.briarproject.masterproject.api.client.NamedGroup;
import org.briarproject.masterproject.api.sharing.Shareable;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class PrivateGroup extends NamedGroup implements Shareable {

	private final Author creator;

	public PrivateGroup(Group group, String name, Author creator, byte[] salt) {
		super(group, name, salt);
		this.creator = creator;
	}

	public Author getCreator() {
		return creator;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof PrivateGroup && super.equals(o);
	}

}
