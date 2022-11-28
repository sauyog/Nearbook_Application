package org.briarproject.masterproject.api.forum.event;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.masterproject.api.conversation.ConversationRequest;
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent;
import org.briarproject.masterproject.api.forum.Forum;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumInvitationRequestReceivedEvent extends
        ConversationMessageReceivedEvent<ConversationRequest<Forum>> {

    public ForumInvitationRequestReceivedEvent(ConversationRequest<Forum> request,
                                               ContactId contactId) {
        super(request, contactId);
    }

}
