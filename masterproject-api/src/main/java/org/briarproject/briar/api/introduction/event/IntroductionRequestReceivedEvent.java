package org.briarproject.masterproject.api.introduction.event;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent;
import org.briarproject.masterproject.api.introduction.IntroductionRequest;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionRequestReceivedEvent
		extends ConversationMessageReceivedEvent<IntroductionRequest> {

	public IntroductionRequestReceivedEvent(
			IntroductionRequest introductionRequest, ContactId contactId) {
		super(introductionRequest, contactId);
	}

}
