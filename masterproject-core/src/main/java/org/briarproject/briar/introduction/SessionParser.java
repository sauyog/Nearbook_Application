package org.briarproject.briar.introduction;

import org.briarproject.bramble.api.FormatException;
import org.briarproject.bramble.api.data.BdfDictionary;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.masterproject.api.client.SessionId;
import org.briarproject.masterproject.api.introduction.Role;
import org.briarproject.nullsafety.NotNullByDefault;

@NotNullByDefault
interface SessionParser {

    BdfDictionary getSessionQuery(SessionId s);

    Role getRole(BdfDictionary d) throws FormatException;

    IntroducerSession parseIntroducerSession(BdfDictionary d)
            throws FormatException;

    IntroduceeSession parseIntroduceeSession(GroupId introducerGroupId,
                                             BdfDictionary d) throws FormatException;

}
