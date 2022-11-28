package org.briarproject.briar.messaging;

import static org.briarproject.bramble.util.StringUtils.utf8IsTooLong;
import static org.briarproject.briar.messaging.MessageTypes.PRIVATE_MESSAGE;
import static org.briarproject.masterproject.api.autodelete.AutoDeleteConstants.NO_AUTO_DELETE_TIMER;
import static org.briarproject.masterproject.api.messaging.MessagingConstants.MAX_PRIVATE_MESSAGE_TEXT_LENGTH;

import org.briarproject.bramble.api.FormatException;
import org.briarproject.bramble.api.client.ClientHelper;
import org.briarproject.bramble.api.data.BdfList;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.Message;
import org.briarproject.masterproject.api.attachment.AttachmentHeader;
import org.briarproject.masterproject.api.messaging.PrivateMessage;
import org.briarproject.masterproject.api.messaging.PrivateMessageFactory;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class PrivateMessageFactoryImpl implements PrivateMessageFactory {

    private final ClientHelper clientHelper;

    @Inject
    PrivateMessageFactoryImpl(ClientHelper clientHelper) {
        this.clientHelper = clientHelper;
    }

    @Override
    public PrivateMessage createLegacyPrivateMessage(GroupId groupId,
                                                     long timestamp, String text) throws FormatException {
        // Validate the arguments
        if (utf8IsTooLong(text, MAX_PRIVATE_MESSAGE_TEXT_LENGTH))
            throw new IllegalArgumentException();
        // Serialise the message
        BdfList body = BdfList.of(text);
        Message m = clientHelper.createMessage(groupId, timestamp, body);
        return new PrivateMessage(m);
    }

    @Override
    public PrivateMessage createPrivateMessage(GroupId groupId, long timestamp,
                                               @Nullable String text, List<AttachmentHeader> headers)
            throws FormatException {
        validateTextAndAttachmentHeaders(text, headers);
        BdfList attachmentList = serialiseAttachmentHeaders(headers);
        // Serialise the message
        BdfList body = BdfList.of(PRIVATE_MESSAGE, text, attachmentList);
        Message m = clientHelper.createMessage(groupId, timestamp, body);
        return new PrivateMessage(m, text != null, headers);
    }

    @Override
    public PrivateMessage createPrivateMessage(GroupId groupId, long timestamp,
                                               @Nullable String text, List<AttachmentHeader> headers,
                                               long autoDeleteTimer) throws FormatException {
        validateTextAndAttachmentHeaders(text, headers);
        BdfList attachmentList = serialiseAttachmentHeaders(headers);
        // Serialise the message
        Long timer = autoDeleteTimer == NO_AUTO_DELETE_TIMER ?
                null : autoDeleteTimer;
        BdfList body = BdfList.of(PRIVATE_MESSAGE, text, attachmentList, timer);
        Message m = clientHelper.createMessage(groupId, timestamp, body);
        return new PrivateMessage(m, text != null, headers, autoDeleteTimer);
    }

    private void validateTextAndAttachmentHeaders(@Nullable String text,
                                                  List<AttachmentHeader> headers) {
        if (text == null) {
            if (headers.isEmpty()) throw new IllegalArgumentException();
        } else if (utf8IsTooLong(text, MAX_PRIVATE_MESSAGE_TEXT_LENGTH)) {
            throw new IllegalArgumentException();
        }
    }

    private BdfList serialiseAttachmentHeaders(List<AttachmentHeader> headers) {
        BdfList attachmentList = new BdfList();
        for (AttachmentHeader a : headers) {
            attachmentList.add(
                    BdfList.of(a.getMessageId(), a.getContentType()));
        }
        return attachmentList;
    }
}
