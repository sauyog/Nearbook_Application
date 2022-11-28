package org.briarproject.masterproject.api.blog.event;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.masterproject.api.blog.Blog;
import org.briarproject.masterproject.api.conversation.ConversationRequest;
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class BlogInvitationRequestReceivedEvent extends
        ConversationMessageReceivedEvent<ConversationRequest<Blog>> {

    public BlogInvitationRequestReceivedEvent(ConversationRequest<Blog> request,
                                              ContactId contactId) {
        super(request, contactId);
    }

}
