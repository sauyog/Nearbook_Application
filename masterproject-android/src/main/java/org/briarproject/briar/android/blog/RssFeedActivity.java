package org.briarproject.masterproject.android.blog;

import static org.briarproject.masterproject.android.blog.RssFeedViewModel.ImportResult.EXISTS;
import static org.briarproject.masterproject.android.blog.RssFeedViewModel.ImportResult.FAILED;
import static org.briarproject.masterproject.android.blog.RssFeedViewModel.ImportResult.IMPORTED;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.activity.BriarActivity;
import org.briarproject.masterproject.android.fragment.BaseFragment.BaseFragmentListener;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class RssFeedActivity extends BriarActivity
        implements BaseFragmentListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private RssFeedViewModel viewModel;

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);

        viewModel = new ViewModelProvider(this, viewModelFactory)
                .get(RssFeedViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_container);

        if (savedInstanceState == null) {
            showInitialFragment(RssFeedManageFragment.newInstance());
        }

        viewModel.getImportResult().observeEvent(this, this::onImportResult);
    }

    private void onImportResult(RssFeedViewModel.ImportResult result) {
        if (result == IMPORTED) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentByTag(RssFeedImportFragment.TAG) != null) {
                onBackPressed();
            }
        } else if (result == FAILED) {
            String url = viewModel.getUrlFailedImport();
            if (url == null) {
                throw new AssertionError();
            }
            RssFeedImportFailedDialogFragment dialog =
                    RssFeedImportFailedDialogFragment.newInstance(url);
            dialog.show(getSupportFragmentManager(),
                    RssFeedImportFailedDialogFragment.TAG);
        } else if (result == EXISTS) {
            Toast.makeText(this, R.string.blogs_rss_feeds_import_exists,
                    Toast.LENGTH_LONG).show();
        }
    }
}
