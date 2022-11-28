package org.briarproject.masterproject.android.contactselection;

import static org.briarproject.masterproject.android.util.UiUtils.GREY_OUT;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.UiThread;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.contact.ContactItemViewHolder;
import org.briarproject.masterproject.android.contact.OnContactClickListener;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;

@UiThread
@NotNullByDefault
public class BaseSelectableContactHolder<I extends SelectableContactItem>
        extends ContactItemViewHolder<I> {

    protected final TextView info;
    private final CheckBox checkBox;

    public BaseSelectableContactHolder(View v) {
        super(v);
        checkBox = v.findViewById(R.id.checkBox);
        info = v.findViewById(R.id.infoView);
    }

    @Override
    protected void bind(I item, @Nullable
            OnContactClickListener<I> listener) {
        super.bind(item, listener);

        if (item.isSelected()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        if (item.isDisabled()) {
            layout.setEnabled(false);
            grayOutItem(true);
        } else {
            layout.setEnabled(true);
            grayOutItem(false);
        }
    }

    protected void grayOutItem(boolean gray) {
        float alpha = gray ? GREY_OUT : 1f;
        avatar.setAlpha(alpha);
        name.setAlpha(alpha);
        checkBox.setAlpha(alpha);
        info.setAlpha(alpha);
    }

}
