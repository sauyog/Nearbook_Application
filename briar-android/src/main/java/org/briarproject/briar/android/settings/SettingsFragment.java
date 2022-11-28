package org.briarproject.briar.android.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.briarproject.briar.R;
import org.briarproject.briar.android.mailbox.MailboxActivity;
import org.briarproject.briar.android.util.ActivityLaunchers.GetImageAdvanced;
import org.briarproject.briar.android.util.ActivityLaunchers.OpenImageDocumentAdvanced;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;

import static android.os.Build.VERSION.SDK_INT;
import static java.util.Objects.requireNonNull;
import static org.briarproject.briar.android.AppModule.getAndroidComponent;
import static org.briarproject.briar.android.TestingConstants.IS_DEBUG_BUILD;
import static org.briarproject.briar.android.util.UiUtils.launchActivityToOpenFile;
import static org.briarproject.briar.android.util.UiUtils.triggerFeedback;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class SettingsFragment extends PreferenceFragmentCompat {

	public static final String SETTINGS_NAMESPACE = "android-ui";

	private static final String PREF_KEY_AVATAR = "pref_key_avatar";
	private static final String PREF_KEY_FEEDBACK = "pref_key_send_feedback";
	private static final String PREF_KEY_DEV = "pref_key_dev";
	private static final String PREF_KEY_EXPLODE = "pref_key_explode";
	private static final String PREF_KEY_MAILBOX = "pref_key_mailbox";

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private SettingsViewModel viewModel;
	private AvatarPreference prefAvatar;

	@Nullable
	private final ActivityResultLauncher<String[]> docLauncher = SDK_INT >= 19 ?
			registerForActivityResult(new OpenImageDocumentAdvanced(),
					this::onImageSelected) :
			null;
	private final ActivityResultLauncher<String> contentLauncher =
			registerForActivityResult(new GetImageAdvanced(),
					this::onImageSelected);

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		getAndroidComponent(context).inject(this);
		viewModel = new ViewModelProvider(requireActivity(), viewModelFactory)
				.get(SettingsViewModel.class);
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		addPreferencesFromResource(R.xml.settings);

		prefAvatar = requireNonNull(findPreference(PREF_KEY_AVATAR));
		if (viewModel.shouldEnableProfilePictures()) {
			prefAvatar.setOnPreferenceClickListener(preference -> {
				launchActivityToOpenFile(requireContext(),
						docLauncher, contentLauncher, "image/*");
				return true;
			});
		} else {
			prefAvatar.setVisible(false);
		}

		Preference prefMailbox =
				requireNonNull(findPreference(PREF_KEY_MAILBOX));
		if (viewModel.shouldEnableMailbox()) {
			prefMailbox.setOnPreferenceClickListener(preference -> {
				Intent i = new Intent(requireContext(), MailboxActivity.class);
				startActivity(i);
				return true;
			});
		} else {
			prefMailbox.setVisible(false);
		}

		Preference prefFeedback =
				requireNonNull(findPreference(PREF_KEY_FEEDBACK));
		prefFeedback.setOnPreferenceClickListener(preference -> {
			triggerFeedback(requireContext());
			return true;
		});

		Preference explode = requireNonNull(findPreference(PREF_KEY_EXPLODE));
		if (IS_DEBUG_BUILD) {
			explode.setOnPreferenceClickListener(preference -> {
				throw new RuntimeException("Boom!");
			});
		} else {
			PreferenceGroup dev = requireNonNull(findPreference(PREF_KEY_DEV));
			dev.setVisible(false);
		}
	}

	@Override
	public void onViewCreated(@NonNull View view,
			@Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		viewModel.getOwnIdentityInfo().observe(getViewLifecycleOwner(), us ->
				prefAvatar.setOwnIdentityInfo(us)
		);
	}

	@Override
	public void onStart() {
		super.onStart();
		requireActivity().setTitle(R.string.settings_button);
	}

	private void onImageSelected(@Nullable Uri uri) {
		if (uri == null) return;
		DialogFragment dialog = ConfirmAvatarDialogFragment.newInstance(uri);
		dialog.show(getParentFragmentManager(),
				ConfirmAvatarDialogFragment.TAG);
	}

}
