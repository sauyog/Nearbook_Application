package org.briarproject.briar.android.mailbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.briarproject.briar.R;
import org.briarproject.briar.android.navdrawer.TransportsActivity;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import static android.view.View.FOCUS_DOWN;
import static org.briarproject.briar.android.AppModule.getAndroidComponent;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class OfflineFragment extends Fragment {

	public static final String TAG = OfflineFragment.class.getName();

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	protected MailboxViewModel viewModel;

	private NestedScrollView scrollView;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		FragmentActivity activity = requireActivity();
		getAndroidComponent(activity).inject(this);
		viewModel = new ViewModelProvider(activity, viewModelFactory)
				.get(MailboxViewModel.class);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_offline, container, false);

		scrollView = (NestedScrollView) v;
		Button checkButton = v.findViewById(R.id.checkButton);
		checkButton.setOnClickListener(view -> {
			Intent i = new Intent(requireContext(), TransportsActivity.class);
			startActivity(i);
		});
		Button buttonView = v.findViewById(R.id.button);
		buttonView.setOnClickListener(view -> onTryAgainClicked());

		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		// Scroll down in case the screen is small, so the button is visible
		scrollView.post(() -> scrollView.fullScroll(FOCUS_DOWN));
	}

	protected void onTryAgainClicked() {
		viewModel.showDownloadFragment();
	}

}
