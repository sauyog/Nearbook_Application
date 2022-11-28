package org.briarproject.masterproject.api.forum;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.bramble.api.sync.Message;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.ThreadedMessage;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ForumPost extends ThreadedMessage {

    public ForumPost(Message message, @Nullable MessageId parent,
                     Author author) {
        super(message, parent, author);
    }

}
