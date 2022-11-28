package org.briarproject.masterproject.android.privategroup.invitation;

import android.content.Context;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.sharing.InvitationActivity;
import org.briarproject.masterproject.android.sharing.InvitationAdapter;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationItem;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

import static org.briarproject.masterproject.android.sharing.InvitationAdapter.InvitationClickListener;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class GroupInvitationActivity
		extends InvitationActivity<GroupInvitationItem> {

	@Inject
	protected GroupInvitationController controller;

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	protected GroupInvitationController getController() {
		return controller;
	}

	@Override
	protected InvitationAdapter<GroupInvitationItem, ?> getAdapter(Context ctx,
			InvitationClickListener<GroupInvitationItem> listener) {
		return new GroupInvitationAdapter(ctx, listener);
	}

	@Override
	protected int getAcceptRes() {
		return R.string.groups_invitations_joined;
	}

	@Override
	protected int getDeclineRes() {
		return R.string.groups_invitations_declined;
	}

}
