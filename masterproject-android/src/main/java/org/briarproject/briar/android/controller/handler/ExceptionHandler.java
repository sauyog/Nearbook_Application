package org.briarproject.masterproject.android.controller.handler;

public interface ExceptionHandler<E extends Exception> {

	void onException(E exception);

}
