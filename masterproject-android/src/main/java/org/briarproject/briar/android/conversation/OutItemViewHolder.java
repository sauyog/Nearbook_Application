package org.briarproject.masterproject.android.conversation;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.UiThread;

import org.briarproject.briar.R;
import org.briarproject.nullsafety.NotNullByDefault;

@UiThread
@NotNullByDefault
class OutItemViewHolder {

    private final ImageView status;

    OutItemViewHolder(View v) {
        status = v.findViewById(R.id.status);
    }

    void bind(ConversationItem item) {
        int res;
        if (item.isSeen()) {
            res = R.drawable.message_delivered;
        } else if (item.isSent()) {
            res = R.drawable.message_sent;
        } else {
            res = R.drawable.message_stored;
        }
        status.setImageResource(res);
    }

}
