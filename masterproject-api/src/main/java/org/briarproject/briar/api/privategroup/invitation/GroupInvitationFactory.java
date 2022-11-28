package org.briarproject.masterproject.api.privategroup.invitation;

import static org.briarproject.masterproject.api.privategroup.invitation.GroupInvitationManager.CLIENT_ID;

import org.briarproject.bramble.api.contact.Contact;
import org.briarproject.bramble.api.crypto.CryptoExecutor;
import org.briarproject.bramble.api.crypto.PrivateKey;
import org.briarproject.bramble.api.data.BdfList;
import org.briarproject.bramble.api.identity.AuthorId;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface GroupInvitationFactory {

    String SIGNING_LABEL_INVITE = CLIENT_ID.getString() + "/INVITE";

    /**
     * Returns a signature to include when inviting a member to join a private
     * group. If the member accepts the invitation, the signature will be
     * included in the member's join message.
     */
    @CryptoExecutor
    byte[] signInvitation(Contact c, GroupId privateGroupId, long timestamp,
                          PrivateKey privateKey);

    /**
     * Returns a token to be signed by the creator when inviting a member to
     * join a private group. If the member accepts the invitation, the
     * signature will be included in the member's join message.
     */
    BdfList createInviteToken(AuthorId creatorId, AuthorId memberId,
                              GroupId privateGroupId, long timestamp);

}
