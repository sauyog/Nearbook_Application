package org.briarproject.masterproject.android.contactselection;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.View;

import androidx.annotation.UiThread;

import org.briarproject.masterproject.android.contact.OnContactClickListener;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;

@UiThread
@NotNullByDefault
class SelectableContactHolder
        extends BaseSelectableContactHolder<SelectableContactItem> {

    SelectableContactHolder(View v) {
        super(v);
    }

    @Override
    protected void bind(SelectableContactItem item, @Nullable
            OnContactClickListener<SelectableContactItem> listener) {
        super.bind(item, listener);

        if (item.isDisabled()) {
            info.setVisibility(VISIBLE);
        } else {
            info.setVisibility(GONE);
        }
    }

}
