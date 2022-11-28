package org.briarproject.masterproject.android.conversation;

import android.view.View;

import androidx.annotation.UiThread;

import org.briarproject.masterproject.android.attachment.AttachmentItem;
import org.briarproject.nullsafety.NotNullByDefault;

@UiThread
@NotNullByDefault
interface ConversationListener {

    void respondToRequest(ConversationRequestItem item, boolean accept);

    void openRequestedShareable(ConversationRequestItem item);

    void onAttachmentClicked(View view, ConversationMessageItem messageItem,
                             AttachmentItem attachmentItem);

    void onAutoDeleteTimerNoticeClicked();

}
