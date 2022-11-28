package org.briarproject.masterproject.android.sharing;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.bramble.api.db.DbException;
import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.masterproject.android.contactselection.ContactSelectorController;
import org.briarproject.masterproject.android.contactselection.SelectableContactItem;
import org.briarproject.masterproject.android.controller.handler.ExceptionHandler;

import java.util.Collection;

import javax.annotation.Nullable;

public interface ShareBlogController
        extends ContactSelectorController<SelectableContactItem> {

    void share(GroupId g, Collection<ContactId> contacts, @Nullable String text,
               ExceptionHandler<DbException> handler);

}
