package org.briarproject.masterproject.android.privategroup.invitation;

import static org.briarproject.masterproject.android.util.UiUtils.getContactDisplayName;

import android.view.View;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.sharing.InvitationAdapter.InvitationClickListener;
import org.briarproject.masterproject.android.sharing.InvitationViewHolder;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationItem;

import javax.annotation.Nullable;

class GroupInvitationViewHolder
        extends InvitationViewHolder<GroupInvitationItem> {

    GroupInvitationViewHolder(View v) {
        super(v);
    }

    @Override
    public void onBind(@Nullable GroupInvitationItem item,
                       InvitationClickListener<GroupInvitationItem> listener) {
        super.onBind(item, listener);
        if (item == null) return;

        sharedBy.setText(
                sharedBy.getContext().getString(R.string.groups_created_by,
                        getContactDisplayName(item.getCreator())));
    }

}