package org.briarproject.masterproject.android.sharing;

import org.briarproject.bramble.api.db.DbException;
import org.briarproject.masterproject.android.controller.ActivityLifecycleController;
import org.briarproject.masterproject.android.controller.handler.ExceptionHandler;
import org.briarproject.masterproject.android.controller.handler.ResultExceptionHandler;
import org.briarproject.masterproject.api.sharing.InvitationItem;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.Collection;

@NotNullByDefault
public interface InvitationController<I extends InvitationItem>
		extends ActivityLifecycleController {

	void loadInvitations(boolean clear,
			ResultExceptionHandler<Collection<I>, DbException> handler);

	void respondToInvitation(I item, boolean accept,
			ExceptionHandler<DbException> handler);

	interface InvitationListener {

		void loadInvitations(boolean clear);

	}

}
