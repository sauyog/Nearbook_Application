package org.briarproject.masterproject.android.blog;

import static org.briarproject.masterproject.android.activity.BriarActivity.GROUP_ID;
import static java.util.Objects.requireNonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import org.briarproject.bramble.api.sync.GroupId;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.BaseActivity;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class RssFeedDeleteFeedDialogFragment extends DialogFragment {
    final static String TAG = RssFeedDeleteFeedDialogFragment.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private RssFeedViewModel viewModel;

    static RssFeedDeleteFeedDialogFragment newInstance(GroupId groupId) {
        Bundle args = new Bundle();
        args.putByteArray(GROUP_ID, groupId.getBytes());
        RssFeedDeleteFeedDialogFragment f =
                new RssFeedDeleteFeedDialogFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        ((BaseActivity) requireActivity()).getActivityComponent().inject(this);

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory)
                .get(RssFeedViewModel.class);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        GroupId groupId = new GroupId(
                requireNonNull(requireArguments().getByteArray(GROUP_ID)));
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(),
                R.style.BriarDialogTheme);
        builder.setTitle(getString(R.string.blogs_rss_remove_feed));
        builder.setMessage(
                getString(R.string.blogs_rss_remove_feed_dialog_message));
        builder.setPositiveButton(R.string.cancel, null);
        builder.setNegativeButton(R.string.blogs_rss_remove_feed_ok,
                (dialog, which) -> viewModel.removeFeed(groupId));
        return builder.create();
    }
}
