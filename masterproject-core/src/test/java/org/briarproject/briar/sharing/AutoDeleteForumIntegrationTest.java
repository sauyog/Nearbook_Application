package org.briarproject.briar.sharing;

import org.briarproject.bramble.api.db.DbException;
import org.briarproject.masterproject.api.conversation.ConversationManager.ConversationClient;
import org.briarproject.masterproject.api.conversation.event.ConversationMessageReceivedEvent;
import org.briarproject.masterproject.api.forum.Forum;
import org.briarproject.masterproject.api.forum.ForumManager;
import org.briarproject.masterproject.api.forum.event.ForumInvitationResponseReceivedEvent;
import org.briarproject.masterproject.api.sharing.InvitationResponse;
import org.briarproject.masterproject.api.sharing.Shareable;
import org.briarproject.masterproject.api.sharing.SharingManager;
import org.briarproject.briar.test.BriarIntegrationTestComponent;
import org.junit.Before;

import java.util.Collection;

public class AutoDeleteForumIntegrationTest
		extends AbstractAutoDeleteIntegrationTest {

	private SharingManager<Forum> sharingManager0;
	private SharingManager<Forum> sharingManager1;
	private Forum shareable;
	private ForumManager manager0;
	private ForumManager manager1;
	private Class<ForumInvitationResponseReceivedEvent>
			responseReceivedEventClass;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		manager0 = c0.getForumManager();
		manager1 = c1.getForumManager();
		shareable = manager0.addForum("Test Forum");
		sharingManager0 = c0.getForumSharingManager();
		sharingManager1 = c1.getForumSharingManager();
		responseReceivedEventClass = ForumInvitationResponseReceivedEvent.class;
	}

	@Override
	protected ConversationClient getConversationClient(
			BriarIntegrationTestComponent component) {
		return component.getForumSharingManager();
	}

	@Override
	protected SharingManager<? extends Shareable> getSharingManager0() {
		return sharingManager0;
	}

	@Override
	protected SharingManager<? extends Shareable> getSharingManager1() {
		return sharingManager1;
	}

	@Override
	protected Shareable getShareable() {
		return shareable;
	}

	@Override
	protected Collection<Forum> subscriptions0() throws DbException {
		return manager0.getForums();
	}

	@Override
	protected Collection<Forum> subscriptions1() throws DbException {
		return manager1.getForums();
	}

	@Override
	protected Class<? extends ConversationMessageReceivedEvent<? extends InvitationResponse>> getResponseReceivedEventClass() {
		return responseReceivedEventClass;
	}
}
