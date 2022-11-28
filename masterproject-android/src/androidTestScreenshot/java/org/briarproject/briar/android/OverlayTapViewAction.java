package org.briarproject.masterproject.android;

import android.view.View;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.action.GeneralLocation.VISIBLE_CENTER;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;

public class OverlayTapViewAction implements ViewAction {

    private final OverlayView overlayView;

    public OverlayTapViewAction(OverlayView overlayView) {
        this.overlayView = overlayView;
    }

    public static ViewAction visualClick(OverlayView overlayView) {
        return new OverlayTapViewAction(overlayView);
    }

    @Override
    public Matcher<View> getConstraints() {
        return isDisplayingAtLeast(90);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(UiController uiController, View view) {
        float[] coordinates = VISIBLE_CENTER.calculateCoordinates(view);
        overlayView.tap(coordinates);
    }
}

