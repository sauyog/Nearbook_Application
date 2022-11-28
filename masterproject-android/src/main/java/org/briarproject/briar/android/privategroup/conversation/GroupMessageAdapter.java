package org.briarproject.masterproject.android.privategroup.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.UiThread;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.threaded.BaseThreadItemViewHolder;
import org.briarproject.masterproject.android.threaded.ThreadItemAdapter;
import org.briarproject.masterproject.android.threaded.ThreadPostViewHolder;
import org.briarproject.nullsafety.NotNullByDefault;

@UiThread
@NotNullByDefault
class GroupMessageAdapter extends ThreadItemAdapter<GroupMessageItem> {

    private boolean isCreator = false;

    GroupMessageAdapter(ThreadItemListener<GroupMessageItem> listener) {
        super(listener);
    }

    @LayoutRes
    @Override
    public int getItemViewType(int position) {
        GroupMessageItem item = getItem(position);
        return item.getLayout();
    }

    @Override
    public BaseThreadItemViewHolder<GroupMessageItem> onCreateViewHolder(
            ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(type, parent, false);
        if (type == R.layout.list_item_group_join_notice) {
            return new JoinMessageItemViewHolder(v, isCreator);
        }
        return new ThreadPostViewHolder<>(v);
    }

    void setIsCreator(boolean isCreator) {
        this.isCreator = isCreator;
        notifyDataSetChanged();
    }

}
