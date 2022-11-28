package org.briarproject.masterproject.android.privategroup.creation;

import static org.briarproject.masterproject.android.activity.BriarActivity.GROUP_ID;

import android.os.Bundle;

import androidx.annotation.Nullable;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.contactselection.ContactSelectorController;
import org.briarproject.masterproject.android.contactselection.ContactSelectorFragment;
import org.briarproject.masterproject.android.contactselection.SelectableContactItem;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class GroupInviteFragment extends ContactSelectorFragment {

    public static final String TAG = GroupInviteFragment.class.getName();

    @Inject
    CreateGroupController controller;

    public static GroupInviteFragment newInstance(GroupId groupId) {
        Bundle args = new Bundle();
        args.putByteArray(GROUP_ID, groupId.getBytes());
        GroupInviteFragment fragment = new GroupInviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectFragment(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle(R.string.groups_invite_members);
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
