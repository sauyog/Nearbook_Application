package org.briarproject.masterproject.android.privategroup.reveal;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.bramble.api.db.DbException;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.masterproject.android.contactselection.ContactSelectorController;
import org.briarproject.masterproject.android.controller.handler.ExceptionHandler;
import org.briarproject.masterproject.android.controller.handler.ResultExceptionHandler;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.Collection;

@NotNullByDefault
public interface RevealContactsController
		extends ContactSelectorController<RevealableContactItem> {

	void isOnboardingNeeded(
			ResultExceptionHandler<Boolean, DbException> handler);

	void onboardingShown(ExceptionHandler<DbException> handler);

	void reveal(GroupId g, Collection<ContactId> contacts,
			ExceptionHandler<DbException> handler);

}
