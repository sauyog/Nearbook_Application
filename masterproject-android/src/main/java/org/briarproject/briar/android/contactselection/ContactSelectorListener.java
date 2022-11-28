package org.briarproject.masterproject.android.contactselection;

import androidx.annotation.UiThread;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.Collection;

@NotNullByDefault
public interface ContactSelectorListener {

    @UiThread
    void contactsSelected(Collection<ContactId> contacts);

}
