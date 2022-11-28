package org.briarproject.briar.introduction;

import static org.briarproject.masterproject.api.autodelete.AutoDeleteConstants.NO_AUTO_DELETE_TIMER;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.SessionId;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class AuthMessage extends AbstractIntroductionMessage {

    private final SessionId sessionId;
    private final byte[] mac, signature;

    protected AuthMessage(MessageId messageId, GroupId groupId,
                          long timestamp, MessageId previousMessageId, SessionId sessionId,
                          byte[] mac, byte[] signature) {
        super(messageId, groupId, timestamp, previousMessageId,
                NO_AUTO_DELETE_TIMER);
        this.sessionId = sessionId;
        this.mac = mac;
        this.signature = signature;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public byte[] getMac() {
        return mac;
    }

    public byte[] getSignature() {
        return signature;
    }

}