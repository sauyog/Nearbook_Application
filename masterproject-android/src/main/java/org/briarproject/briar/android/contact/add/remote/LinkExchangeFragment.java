package org.briarproject.masterproject.android.contact.add.remote;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;
import static org.briarproject.bramble.api.contact.HandshakeLinkConstants.LINK_REGEX;
import static org.briarproject.masterproject.android.util.UiUtils.hideViewOnSmallScreen;
import static org.briarproject.masterproject.android.util.UiUtils.observeOnce;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ShareCompat.IntentBuilder;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.fragment.BaseFragment;
import org.briarproject.masterproject.android.view.InfoView;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import java.util.regex.Matcher;

import javax.annotation.Nullable;
import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class LinkExchangeFragment extends BaseFragment {

    private static final String TAG = LinkExchangeFragment.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AddContactViewModel viewModel;

    private ClipboardManager clipboard;
    private TextInputLayout linkInputLayout;
    private TextInputEditText linkInput;

    @Override
    public String getUniqueTag() {
        return TAG;
    }

    @Override
    public void injectFragment(ActivityComponent component) {
        component.inject(this);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory)
                .get(AddContactViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getActivity() == null || getContext() == null) return null;

        View v = inflater.inflate(R.layout.fragment_link_exchange,
                container, false);

        linkInputLayout = v.findViewById(R.id.linkInputLayout);
        linkInput = v.findViewById(R.id.linkInput);
        if (viewModel.getRemoteHandshakeLink() != null) {
            // This can happen if the link was set via an incoming Intent
            linkInput.setText(viewModel.getRemoteHandshakeLink());
        }

        clipboard = (ClipboardManager)
                requireContext().getSystemService(CLIPBOARD_SERVICE);

        Button pasteButton = v.findViewById(R.id.pasteButton);
        pasteButton.setOnClickListener(view -> {
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0)
                linkInput.setText(clipData.getItemAt(0).getText());
        });

        observeOnce(viewModel.getHandshakeLink(), this,
                this::onHandshakeLinkLoaded);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        hideViewOnSmallScreen(requireView().findViewById(R.id.imageView));
    }

    private void onHandshakeLinkLoaded(String link) {
        View v = requireView();

        TextView linkView = v.findViewById(R.id.linkView);
        linkView.setText(link);

        Button copyButton = v.findViewById(R.id.copyButton);
        ClipData clip = ClipData.newPlainText(
                getString(R.string.link_clip_label), link);
        copyButton.setOnClickListener(view -> {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.link_copied_toast,
                    LENGTH_SHORT).show();
        });
        copyButton.setEnabled(true);

        Button shareButton = v.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(view ->
                IntentBuilder.from(requireActivity())
                        .setText(link)
                        .setType("text/plain")
                        .startChooser());
        shareButton.setEnabled(true);

        InfoView infoText = v.findViewById(R.id.infoView);
        infoText.setText(R.string.info_both_must_enter_links);

        Button continueButton = v.findViewById(R.id.addButton);
        continueButton.setOnClickListener(view -> onContinueButtonClicked());
        continueButton.setEnabled(true);
    }

    /**
     * Requires {@link AddContactViewModel#getHandshakeLink()} to be loaded.
     */
    @Nullable
    private String getRemoteHandshakeLinkOrNull() {
        CharSequence link = linkInput.getText();
        if (link == null || link.length() == 0) {
            linkInputLayout.setError(getString(R.string.missing_link));
            linkInput.requestFocus();
            return null;
        }

        Matcher matcher = LINK_REGEX.matcher(link);
        if (matcher.find()) {
            String linkWithoutSchema = matcher.group(2);
            // Check also if this is our own link. This was loaded already,
            // because it enables the Continue button which is the only caller.
            if (("briar://" + linkWithoutSchema)
                    .equals(viewModel.getHandshakeLink().getValue())) {
                linkInputLayout.setError(getString(R.string.own_link_error));
                linkInput.requestFocus();
                return null;
            }
            linkInputLayout.setError(null);
            return link.toString();
        }
        linkInputLayout.setError(getString(R.string.invalid_link));
        linkInput.requestFocus();
        return null;
    }

    private void onContinueButtonClicked() {
        String link = getRemoteHandshakeLinkOrNull();
        if (link == null) return;  // invalid link

        viewModel.setRemoteHandshakeLink(link);
        viewModel.onRemoteLinkEntered();
    }

}
