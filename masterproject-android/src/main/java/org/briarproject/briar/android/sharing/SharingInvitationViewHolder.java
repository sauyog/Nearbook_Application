package org.briarproject.masterproject.android.sharing;

import static org.briarproject.masterproject.android.util.UiUtils.getContactDisplayName;

import android.view.View;

import org.briarproject.bramble.api.contact.Contact;
import org.briarproject.bramble.util.StringUtils;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.sharing.InvitationAdapter.InvitationClickListener;
import org.briarproject.masterproject.api.sharing.SharingInvitationItem;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nullable;

class SharingInvitationViewHolder
        extends InvitationViewHolder<SharingInvitationItem> {

    SharingInvitationViewHolder(View v) {
        super(v);
    }

    @Override
    public void onBind(@Nullable SharingInvitationItem item,
                       InvitationClickListener<SharingInvitationItem> listener) {
        super.onBind(item, listener);
        if (item == null) return;

        Collection<String> names = new ArrayList<>();
        for (Contact c : item.getNewSharers())
            names.add(getContactDisplayName(c));
        sharedBy.setText(
                sharedBy.getContext().getString(R.string.shared_by_format,
                        StringUtils.join(names, ", ")));
    }

}
