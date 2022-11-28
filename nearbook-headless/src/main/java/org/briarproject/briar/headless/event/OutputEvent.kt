package org.briarproject.briar.headless.event

import org.briarproject.briar.headless.json.JsonDict
import org.briarproject.briar.headless.messaging.output
import org.briarproject.masterproject.api.blog.BlogInvitationRequest
import org.briarproject.masterproject.api.blog.BlogInvitationResponse
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent
import org.briarproject.masterproject.api.forum.ForumInvitationRequest
import org.briarproject.masterproject.api.forum.ForumInvitationResponse
import org.briarproject.masterproject.api.introduction.IntroductionRequest
import org.briarproject.masterproject.api.introduction.IntroductionResponse
import org.briarproject.masterproject.api.messaging.PrivateMessageHeader
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationRequest
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationResponse
import javax.annotation.concurrent.Immutable

@Immutable
@Suppress("unused")
internal class OutputEvent(val name: String, val data: JsonDict) {
    val type = "event"
}

internal fun ConversationMessageReceivedEvent<*>.output(text: String?): JsonDict {
    check(messageHeader is PrivateMessageHeader)
    return (messageHeader as PrivateMessageHeader).output(contactId, text)
}

internal fun ConversationMessageReceivedEvent<*>.output() = when (messageHeader) {
    // requests
    is ForumInvitationRequest -> (messageHeader as ForumInvitationRequest).output(contactId)
    is BlogInvitationRequest -> (messageHeader as BlogInvitationRequest).output(contactId)
    is GroupInvitationRequest -> (messageHeader as GroupInvitationRequest).output(contactId)
    is IntroductionRequest -> (messageHeader as IntroductionRequest).output(contactId)
    // responses
    is ForumInvitationResponse -> (messageHeader as ForumInvitationResponse).output(contactId)
    is BlogInvitationResponse -> (messageHeader as BlogInvitationResponse).output(contactId)
    is GroupInvitationResponse -> (messageHeader as GroupInvitationResponse).output(contactId)
    is IntroductionResponse -> (messageHeader as IntroductionResponse).output(contactId)
    // unknown
    else -> throw IllegalStateException()
}
