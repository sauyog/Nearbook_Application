package org.briarproject.masterproject.android.sharing;

import static org.briarproject.masterproject.android.activity.BriarActivity.GROUP_ID;

import android.os.Bundle;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.contactselection.ContactSelectorController;
import org.briarproject.masterproject.android.contactselection.ContactSelectorFragment;
import org.briarproject.masterproject.android.contactselection.SelectableContactItem;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class ShareBlogFragment extends ContactSelectorFragment {

    public static final String TAG = ShareBlogFragment.class.getName();

    @Inject
    ShareBlogController controller;

    public static ShareBlogFragment newInstance(GroupId groupId) {
        Bundle args = new Bundle();
        args.putByteArray(GROUP_ID, groupId.getBytes());
        ShareBlogFragment fragment = new ShareBlogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectFragment(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected ContactSelectorController<SelectableContactItem> getController() {
        return controller;
    }

    @Override
    public String getUniqueTag() {
        return TAG;
    }

}
