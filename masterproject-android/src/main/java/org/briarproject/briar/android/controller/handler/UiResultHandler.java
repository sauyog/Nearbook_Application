package org.briarproject.masterproject.android.controller.handler;

import androidx.annotation.UiThread;

import org.briarproject.masterproject.android.DestroyableContext;

public abstract class UiResultHandler<R> implements ResultHandler<R> {

    private final DestroyableContext listener;

    protected UiResultHandler(DestroyableContext listener) {
        this.listener = listener;
    }

    @Override
    public void onResult(R result) {
        listener.runOnUiThreadUnlessDestroyed(() -> onResultUi(result));
    }

    @UiThread
    public abstract void onResultUi(R result);
}
