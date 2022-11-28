package org.briarproject.masterproject.android.splash;

import static android.content.Intent.ACTION_VIEW;
import static android.view.WindowManager.LayoutParams.FLAG_SECURE;
import static org.briarproject.masterproject.android.TestingConstants.PREVENT_SCREENSHOTS;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.Localizer;

public class ExpiredActivity extends AppCompatActivity
        implements OnClickListener {

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        if (PREVENT_SCREENSHOTS) getWindow().addFlags(FLAG_SECURE);

        setContentView(R.layout.activity_expired);
        findViewById(R.id.download_briar_button).setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(
                Localizer.getInstance().setLocale(base));
        Localizer.getInstance().setLocale(this);
    }

    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("https://briarproject.org/download.html");
        startActivity(new Intent(ACTION_VIEW, uri));
        finish();
    }
}
