package org.briarproject.masterproject.android;

public interface DestroyableContext {

    void runOnUiThreadUnlessDestroyed(Runnable runnable);
}
