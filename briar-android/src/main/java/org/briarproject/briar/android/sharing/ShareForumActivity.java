package org.briarproject.masterproject.android.sharing;

import android.os.Bundle;
import android.widget.Toast;

import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.bramble.api.db.DbException;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.controller.handler.UiExceptionHandler;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import java.util.Collection;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static android.widget.Toast.LENGTH_SHORT;
import static org.briarproject.briar.api.sharing.SharingConstants.MAX_INVITATION_TEXT_LENGTH;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class ShareForumActivity extends ShareActivity {

	@Inject
	ShareForumController controller;

	@Override
	BaseMessageFragment getMessageFragment() {
		return ShareForumMessageFragment.newInstance();
	}

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(@Nullable Bundle bundle) {
		super.onCreate(bundle);

		if (bundle == null) {
			showInitialFragment(ShareForumFragment.newInstance(groupId));
		}
	}

	@Override
	public int getMaximumTextLength() {
		return MAX_INVITATION_TEXT_LENGTH;
	}

	@Override
	void share(Collection<ContactId> contacts, @Nullable String text) {
		controller.share(groupId, contacts, text,
				new UiExceptionHandler<DbException>(this) {
					@Override
					public void onExceptionUi(DbException exception) {
						Toast.makeText(ShareForumActivity.this,
								R.string.forum_share_error, LENGTH_SHORT)
								.show();
						handleException(exception);
					}
				});
	}

}
