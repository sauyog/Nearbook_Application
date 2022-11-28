package org.briarproject.masterproject.api.introduction;

import static org.briarproject.masterproject.api.introduction.Role.INTRODUCER;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.SessionId;
import org.briarproject.masterproject.api.conversation.ConversationMessageVisitor;
import org.briarproject.masterproject.api.conversation.ConversationResponse;
import org.briarproject.masterproject.api.identity.AuthorInfo;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionResponse extends ConversationResponse {

    private final Author introducedAuthor;
    private final AuthorInfo introducedAuthorInfo;
    private final Role ourRole;
    private final boolean canSucceed;

    public IntroductionResponse(MessageId messageId, GroupId groupId, long time,
                                boolean local, boolean read, boolean sent, boolean seen,
                                SessionId sessionId, boolean accepted, Author author,
                                AuthorInfo introducedAuthorInfo, Role role, boolean canSucceed,
                                long autoDeleteTimer, boolean isAutoDecline) {
        super(messageId, groupId, time, local, read, sent, seen, sessionId,
                accepted, autoDeleteTimer, isAutoDecline);
        this.introducedAuthor = author;
        this.introducedAuthorInfo = introducedAuthorInfo;
        this.ourRole = role;
        this.canSucceed = canSucceed;
    }

    public Author getIntroducedAuthor() {
        return introducedAuthor;
    }

    public AuthorInfo getIntroducedAuthorInfo() {
        return introducedAuthorInfo;
    }

    public boolean canSucceed() {
        return canSucceed;
    }

    public boolean isIntroducer() {
        return ourRole == INTRODUCER;
    }

    @Override
    public <T> T accept(ConversationMessageVisitor<T> v) {
        return v.visitIntroductionResponse(this);
    }

}
