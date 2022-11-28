package org.briarproject.masterproject.api.blog.event;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.masterproject.api.blog.BlogInvitationResponse;
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class BlogInvitationResponseReceivedEvent
        extends ConversationMessageReceivedEvent<BlogInvitationResponse> {

    public BlogInvitationResponseReceivedEvent(BlogInvitationResponse response,
                                               ContactId contactId) {
        super(response, contactId);
    }

}
