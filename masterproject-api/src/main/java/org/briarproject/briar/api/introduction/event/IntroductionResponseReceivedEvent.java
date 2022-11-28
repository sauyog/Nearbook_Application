package org.briarproject.masterproject.api.introduction.event;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent;
import org.briarproject.masterproject.api.introduction.IntroductionResponse;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionResponseReceivedEvent extends
        ConversationMessageReceivedEvent<IntroductionResponse> {

    public IntroductionResponseReceivedEvent(
            IntroductionResponse introductionResponse, ContactId contactId) {
        super(introductionResponse, contactId);
    }

}
