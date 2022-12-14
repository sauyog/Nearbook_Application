package org.briarproject.masterproject.android.contactselection;

import android.content.Context;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.masterproject.android.contact.BaseContactListAdapter;
import org.briarproject.masterproject.android.contact.ContactItemViewHolder;
import org.briarproject.masterproject.android.contact.OnContactClickListener;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.ArrayList;
import java.util.Collection;

@NotNullByDefault
public abstract class BaseContactSelectorAdapter<I extends SelectableContactItem, H extends ContactItemViewHolder<I>>
		extends BaseContactListAdapter<I, H> {

	public BaseContactSelectorAdapter(Context context, Class<I> c,
			OnContactClickListener<I> listener) {
		super(context, c, listener);
	}

	public Collection<ContactId> getSelectedContactIds() {
		Collection<ContactId> selected = new ArrayList<>();

		for (int i = 0; i < items.size(); i++) {
			SelectableContactItem item = items.get(i);
			if (item.isSelected()) selected.add(item.getContact().getId());
		}
		return selected;
	}

}
