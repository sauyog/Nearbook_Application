package org.briarproject.masterproject.api.conversation;

import org.briarproject.masterproject.api.blog.BlogInvitationRequest;
import org.briarproject.masterproject.api.blog.BlogInvitationResponse;
import org.briarproject.masterproject.api.forum.ForumInvitationRequest;
import org.briarproject.masterproject.api.forum.ForumInvitationResponse;
import org.briarproject.masterproject.api.introduction.IntroductionRequest;
import org.briarproject.masterproject.api.introduction.IntroductionResponse;
import org.briarproject.masterproject.api.messaging.PrivateMessageHeader;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationRequest;
import org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationResponse;
import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface ConversationMessageVisitor<T> {

	T visitPrivateMessageHeader(PrivateMessageHeader h);

	T visitBlogInvitationRequest(BlogInvitationRequest r);

	T visitBlogInvitationResponse(BlogInvitationResponse r);

	T visitForumInvitationRequest(ForumInvitationRequest r);

	T visitForumInvitationResponse(ForumInvitationResponse r);

	T visitGroupInvitationRequest(GroupInvitationRequest r);

	T visitGroupInvitationResponse(GroupInvitationResponse r);

	T visitIntroductionRequest(IntroductionRequest r);

	T visitIntroductionResponse(IntroductionResponse r);
}
