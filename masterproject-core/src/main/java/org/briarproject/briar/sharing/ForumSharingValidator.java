package org.briarproject.briar.sharing;

import static org.briarproject.bramble.util.ValidationUtils.checkLength;
import static org.briarproject.bramble.util.ValidationUtils.checkSize;
import static org.briarproject.masterproject.api.forum.ForumConstants.FORUM_SALT_LENGTH;
import static org.briarproject.masterproject.api.forum.ForumConstants.MAX_FORUM_NAME_LENGTH;

import org.briarproject.bramble.api.FormatException;
import org.briarproject.bramble.api.client.ClientHelper;
import org.briarproject.bramble.api.data.BdfList;
import org.briarproject.bramble.api.data.MetadataEncoder;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.bramble.api.system.Clock;
import org.briarproject.masterproject.api.forum.Forum;
import org.briarproject.masterproject.api.forum.ForumFactory;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class ForumSharingValidator extends SharingValidator {

    private final ForumFactory forumFactory;

    @Inject
    ForumSharingValidator(MessageEncoder messageEncoder,
                          ClientHelper clientHelper, MetadataEncoder metadataEncoder,
                          Clock clock, ForumFactory forumFactory) {
        super(messageEncoder, clientHelper, metadataEncoder, clock);
        this.forumFactory = forumFactory;
    }

    @Override
    protected GroupId validateDescriptor(BdfList descriptor)
            throws FormatException {
        // Name, salt
        checkSize(descriptor, 2);
        String name = descriptor.getString(0);
        checkLength(name, 1, MAX_FORUM_NAME_LENGTH);
        byte[] salt = descriptor.getRaw(1);
        checkLength(salt, FORUM_SALT_LENGTH);
        Forum forum = forumFactory.createForum(name, salt);
        return forum.getId();
    }

}
