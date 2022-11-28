package org.briarproject.masterproject.api.messaging;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.attachment.AttachmentHeader;
import org.briarproject.masterproject.api.conversation.ConversationMessageHeader;
import org.briarproject.masterproject.api.conversation.ConversationMessageVisitor;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class PrivateMessageHeader extends ConversationMessageHeader {

    private final boolean hasText;
    private final List<AttachmentHeader> attachmentHeaders;

    public PrivateMessageHeader(MessageId id, GroupId groupId, long timestamp,
                                boolean local, boolean read, boolean sent, boolean seen,
                                boolean hasText, List<AttachmentHeader> headers,
                                long autoDeleteTimer) {
        super(id, groupId, timestamp, local, read, sent, seen, autoDeleteTimer);
        this.hasText = hasText;
        this.attachmentHeaders = headers;
    }

    public boolean hasText() {
        return hasText;
    }

    public List<AttachmentHeader> getAttachmentHeaders() {
        return attachmentHeaders;
    }

    @Override
    public <T> T accept(ConversationMessageVisitor<T> v) {
        return v.visitPrivateMessageHeader(this);
    }
}
