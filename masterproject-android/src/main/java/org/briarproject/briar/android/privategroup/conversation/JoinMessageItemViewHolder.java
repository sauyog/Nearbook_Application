package org.briarproject.masterproject.android.privategroup.conversation;

import static org.briarproject.masterproject.api.identity.AuthorInfo.Status.OURSELVES;

import android.content.Context;
import android.view.View;

import androidx.annotation.UiThread;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.threaded.BaseThreadItemViewHolder;
import org.briarproject.masterproject.android.threaded.ThreadItemAdapter.ThreadItemListener;
import org.briarproject.nullsafety.NotNullByDefault;

@UiThread
@NotNullByDefault
class JoinMessageItemViewHolder
        extends BaseThreadItemViewHolder<GroupMessageItem> {

    private final boolean isCreator;

    JoinMessageItemViewHolder(View v, boolean isCreator) {
        super(v);
        this.isCreator = isCreator;
    }

    @Override
    public void bind(GroupMessageItem item,
                     ThreadItemListener<GroupMessageItem> listener) {
        super.bind(item, listener);

        if (isCreator) bindForCreator((JoinMessageItem) item);
        else bind((JoinMessageItem) item);
    }

    private void bindForCreator(JoinMessageItem item) {
        if (item.isInitial()) {
            textView.setText(R.string.groups_member_created_you);
        } else {
            String name = item.getAuthorName();
            textView.setText(getContext()
                    .getString(R.string.groups_member_joined, name));
        }
    }

    private void bind(JoinMessageItem item) {
        Context ctx = getContext();
        String name = item.getAuthorName();

        if (item.isInitial()) {
            textView.setText(
                    ctx.getString(R.string.groups_member_created, name));
        } else {
            if (item.getAuthorInfo().getStatus() == OURSELVES) {
                textView.setText(R.string.groups_member_joined_you);
            } else {
                textView.setText(
                        ctx.getString(R.string.groups_member_joined, name));
            }
        }
    }

}
