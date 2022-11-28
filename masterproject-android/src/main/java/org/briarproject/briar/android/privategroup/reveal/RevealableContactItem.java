package org.briarproject.masterproject.android.privategroup.reveal;

import org.briarproject.bramble.api.contact.Contact;
import org.briarproject.masterproject.android.contactselection.SelectableContactItem;
import org.briarproject.masterproject.api.identity.AuthorInfo;
import org.briarproject.masterproject.api.privategroup.Visibility;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
class RevealableContactItem extends SelectableContactItem {

	private final Visibility visibility;

	RevealableContactItem(Contact contact, AuthorInfo authorInfo,
			boolean selected, boolean disabled, Visibility visibility) {
		super(contact, authorInfo, selected, disabled);
		this.visibility = visibility;
	}

	Visibility getVisibility() {
		return visibility;
	}

}
