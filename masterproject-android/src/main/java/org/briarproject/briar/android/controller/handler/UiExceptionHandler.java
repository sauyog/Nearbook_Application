package org.briarproject.masterproject.android.controller.handler;

import androidx.annotation.UiThread;

import org.briarproject.masterproject.android.DestroyableContext;
import org.briarproject.nullsafety.NotNullByDefault;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class UiExceptionHandler<E extends Exception>
        implements ExceptionHandler<E> {

    protected final DestroyableContext listener;

    protected UiExceptionHandler(DestroyableContext listener) {
        this.listener = listener;
    }

    @Override
    public void onException(E exception) {
        listener.runOnUiThreadUnlessDestroyed(() -> onExceptionUi(exception));
    }

    @UiThread
    public abstract void onExceptionUi(E exception);

}
