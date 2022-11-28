package org.briarproject.masterproject.api.privategroup;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.bramble.api.sync.Message;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.ThreadedMessage;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class GroupMessage extends ThreadedMessage {

    public GroupMessage(Message message, @Nullable MessageId parent,
                        Author member) {
        super(message, parent, member);
    }

    public Author getMember() {
        return super.getAuthor();
    }

}
