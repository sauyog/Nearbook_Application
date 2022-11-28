package org.briarproject.masterproject.api.privategroup;

import org.briarproject.bramble.api.identity.Author;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.sync.MessageId;
import org.briarproject.masterproject.api.client.PostHeader;
import org.briarproject.masterproject.api.identity.AuthorInfo;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class GroupMessageHeader extends PostHeader {

    private final GroupId groupId;

    public GroupMessageHeader(GroupId groupId, MessageId id,
                              @Nullable MessageId parentId, long timestamp,
                              Author author, AuthorInfo authorInfo, boolean read) {
        super(id, parentId, timestamp, author, authorInfo, read);
        this.groupId = groupId;
    }

    public GroupId getGroupId() {
        return groupId;
    }

}
