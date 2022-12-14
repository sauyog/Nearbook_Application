package org.briarproject.masterproject.android.forum;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.masterproject.api.identity.AuthorInfo;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.android.threaded.ThreadItem;
import org.briarproject.masterproject.api.forum.ForumPostHeader;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class ForumPostItem extends ThreadItem {

	ForumPostItem(ForumPostHeader h, String text) {
		super(h.getId(), h.getParentId(), text, h.getTimestamp(), h.getAuthor(),
				h.getAuthorInfo(), h.isRead());
	}

}
