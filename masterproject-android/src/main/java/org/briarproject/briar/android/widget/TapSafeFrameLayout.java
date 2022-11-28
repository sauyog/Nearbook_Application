package org.briarproject.masterproject.android.widget;

import static android.view.MotionEvent.FLAG_WINDOW_IS_OBSCURED;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;

import org.briarproject.masterproject.android.util.UiUtils;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.Nullable;

@NotNullByDefault
public class TapSafeFrameLayout extends FrameLayout {

    @Nullable
    private OnTapFilteredListener listener;

    public TapSafeFrameLayout(Context context) {
        super(context);
        UiUtils.setFilterTouchesWhenObscured(this, false);
    }

    public TapSafeFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        UiUtils.setFilterTouchesWhenObscured(this, false);
    }

    public TapSafeFrameLayout(Context context, @Nullable AttributeSet attrs,
                              @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        UiUtils.setFilterTouchesWhenObscured(this, false);
    }

    public void setOnTapFilteredListener(OnTapFilteredListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent e) {
        boolean obscured = (e.getFlags() & FLAG_WINDOW_IS_OBSCURED) != 0;
        if (obscured && listener != null) return listener.shouldAllowTap();
        else return !obscured;
    }

    public interface OnTapFilteredListener {
        boolean shouldAllowTap();
    }
}
