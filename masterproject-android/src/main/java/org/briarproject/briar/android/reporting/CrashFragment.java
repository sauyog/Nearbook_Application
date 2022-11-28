package org.briarproject.masterproject.android.reporting;

import static org.briarproject.masterproject.android.util.UiUtils.hideViewOnSmallScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.fragment.BaseFragment;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class CrashFragment extends BaseFragment {

    public final static String TAG = CrashFragment.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ReportViewModel viewModel;

    @Override
    public void injectFragment(ActivityComponent component) {
        component.inject(this);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory)
                .get(ReportViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater
                .inflate(R.layout.fragment_crash, container, false);

        v.findViewById(R.id.acceptButton).setOnClickListener(view ->
                viewModel.showReport());
        v.findViewById(R.id.declineButton).setOnClickListener(view ->
                viewModel.closeReport());

        return v;
    }

    @Override
    public String getUniqueTag() {
        return TAG;
    }

    @Override
    public void onStart() {
        super.onStart();
        hideViewOnSmallScreen(requireView().findViewById(R.id.errorIcon));
    }
}
