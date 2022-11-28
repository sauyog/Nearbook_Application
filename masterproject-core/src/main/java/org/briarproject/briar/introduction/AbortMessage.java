package org.briarproject.briar.introduction;

import static org.briarproject.masterproject.api.autodelete.AutoDeleteConstants.NO_AUTO_DELETE_TIMER;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.SessionId;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
class AbortMessage extends AbstractIntroductionMessage {

    private final SessionId sessionId;

    protected AbortMessage(MessageId messageId, GroupId groupId, long timestamp,
                           @Nullable MessageId previousMessageId, SessionId sessionId) {
        super(messageId, groupId, timestamp, previousMessageId,
                NO_AUTO_DELETE_TIMER);
        this.sessionId = sessionId;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

}
