package org.briarproject.masterproject.android.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatImageView;

import org.briarproject.briar.R;
import org.briarproject.masterproject.api.identity.AuthorInfo.Status;

@UiThread
public class TrustIndicatorView extends AppCompatImageView {

    public TrustIndicatorView(Context context) {
        super(context);
    }

    public TrustIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrustIndicatorView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTrustLevel(Status status) {
        int res;
        switch (status) {
            case UNVERIFIED:
                res = R.drawable.trust_indicator_unverified;
                break;
            case VERIFIED:
                res = R.drawable.trust_indicator_verified;
                break;
            case OURSELVES:
                res = R.drawable.ic_our_identity;
                break;
            default:
                res = R.drawable.trust_indicator_unknown;
        }
        setImageResource(res);
        setVisibility(VISIBLE);

        invalidate();
        requestLayout();
    }

}
