package org.briarproject.masterproject.api.forum;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.SessionId;
import org.briarproject.masterproject.api.conversation.ConversationMessageVisitor;
import org.briarproject.masterproject.api.sharing.InvitationResponse;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumInvitationResponse extends InvitationResponse {

	public ForumInvitationResponse(MessageId id, GroupId groupId, long time,
			boolean local, boolean read, boolean sent, boolean seen,
			SessionId sessionId, boolean accept, GroupId shareableId,
			long autoDeleteTimer, boolean isAutoDecline) {
		super(id, groupId, time, local, read, sent, seen, sessionId,
				accept, shareableId, autoDeleteTimer, isAutoDecline);
	}

	@Override
	public <T> T accept(ConversationMessageVisitor<T> v) {
		return v.visitForumInvitationResponse(this);
	}
}
