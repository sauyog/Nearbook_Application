package org.briarproject.masterproject.android.privategroup.conversation;

import androidx.annotation.LayoutRes;
import androidx.annotation.UiThread;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.threaded.ThreadItem;
import org.briarproject.masterproject.api.identity.AuthorInfo;
import org.briarproject.masterproject.api.privategroup.GroupMessageHeader;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@UiThread
@NotThreadSafe
class GroupMessageItem extends ThreadItem {

    private final GroupId groupId;

    private GroupMessageItem(MessageId messageId, GroupId groupId,
                             @Nullable MessageId parentId, String text, long timestamp,
                             Author author, AuthorInfo authorInfo, boolean isRead) {
        super(messageId, parentId, text, timestamp, author, authorInfo, isRead);
        this.groupId = groupId;
    }

    GroupMessageItem(GroupMessageHeader h, String text) {
        this(h.getId(), h.getGroupId(), h.getParentId(), text, h.getTimestamp(),
                h.getAuthor(), h.getAuthorInfo(), h.isRead());
    }

    public GroupId getGroupId() {
        return groupId;
    }

    @LayoutRes
    public int getLayout() {
        return R.layout.list_item_thread;
    }

}
