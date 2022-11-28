package org.briarproject.masterproject.android.blog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.BaseActivity;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class RssFeedImportFailedDialogFragment extends DialogFragment {
    final static String TAG = RssFeedImportFailedDialogFragment.class.getName();
    private static final String ARG_URL = "url";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private RssFeedViewModel viewModel;

    static RssFeedImportFailedDialogFragment newInstance(String retryUrl) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, retryUrl);
        RssFeedImportFailedDialogFragment f =
                new RssFeedImportFailedDialogFragment();
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
        AlertDialog.Builder builder =
                new AlertDialog.Builder(requireActivity(),
                        R.style.BriarDialogTheme);
        builder.setMessage(R.string.blogs_rss_feeds_import_error);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.try_again_button, (dialog, which) ->
                viewModel.importFeed(requireArguments().getString(ARG_URL)));

        return builder.create();
    }
}
