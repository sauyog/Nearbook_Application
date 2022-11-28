package org.briarproject.masterproject.android.privategroup.creation;

import androidx.annotation.Nullable;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.bramble.api.db.DbException;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.masterproject.android.contactselection.ContactSelectorController;
import org.briarproject.masterproject.android.contactselection.SelectableContactItem;
import org.briarproject.masterproject.android.controller.handler.ResultExceptionHandler;
import org.briarproject.nullsafety.NotNullByDefault;

import java.util.Collection;

@NotNullByDefault
public interface CreateGroupController
        extends ContactSelectorController<SelectableContactItem> {

    void createGroup(String name,
                     ResultExceptionHandler<GroupId, DbException> result);

    void sendInvitation(GroupId g, Collection<ContactId> contacts,
                        @Nullable String text,
                        ResultExceptionHandler<Void, DbException> result);

}
