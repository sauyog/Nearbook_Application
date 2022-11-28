package org.briarproject.masterproject.android.splash;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.os.Build.VERSION.SDK_INT;
import static androidx.preference.PreferenceManager.setDefaultValues;
import static org.briarproject.masterproject.android.BriarApplication.ENTRY_ACTIVITY;
import static org.briarproject.masterproject.android.TestingConstants.EXPIRY_DATE;
import static org.briarproject.masterproject.android.TestingConstants.IS_DEBUG_BUILD;
import static java.lang.System.currentTimeMillis;
import static java.util.logging.Logger.getLogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;

import org.briarproject.bramble.api.account.AccountManager;
import org.briarproject.bramble.api.system.AndroidExecutor;
import org.briarproject.briar.R;
import org.briarproject.masterproject.android.activity.ActivityComponent;
import org.briarproject.masterproject.android.activity.BaseActivity;
import org.briarproject.nullsafety.MethodsNotNullByDefault;
import org.briarproject.nullsafety.ParametersNotNullByDefault;

import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class SplashScreenActivity extends BaseActivity {

    private static final Logger LOG =
            getLogger(SplashScreenActivity.class.getName());

    @Inject
    protected AccountManager accountManager;
    @Inject
    protected AndroidExecutor androidExecutor;

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);

        if (SDK_INT >= 21) {
            getWindow().setExitTransition(new Fade());
        }

        setPreferencesDefaults();

        setContentView(R.layout.splash);

        if (accountManager.hasDatabaseKey()) {
            startNextActivity(ENTRY_ACTIVITY);
            finish();
        } else {
            int duration =
                    getResources().getInteger(R.integer.splashScreenDuration);
            new Handler().postDelayed(() -> {
                if (currentTimeMillis() >= EXPIRY_DATE) {
                    if (IS_DEBUG_BUILD) {
                        LOG.info("Expired: debug build");
                        startNextActivity(ExpiredActivity.class);
                    } else {
                        LOG.info("Expired: running on old Android");
                        startNextActivity(ExpiredOldAndroidActivity.class);
                    }
                } else {
                    startNextActivity(ENTRY_ACTIVITY);
                }
                supportFinishAfterTransition();
            }, duration);
        }
    }

    private void startNextActivity(Class<? extends Activity> activityClass) {
        Intent i = new Intent(this, activityClass);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void setPreferencesDefaults() {
        androidExecutor.runOnBackgroundThread(
                () -> setDefaultValues(SplashScreenActivity.this,
                        R.xml.panic_preferences, false));
    }

    // Don't show any warnings here
    @Override
    public boolean shouldAllowTap() {
        return true;
    }
}
