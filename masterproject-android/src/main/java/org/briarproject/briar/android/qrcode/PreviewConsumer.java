package org.briarproject.masterproject.android.qrcode;

import android.hardware.Camera;

import org.briarproject.nullsafety.NotNullByDefault;

import androidx.annotation.UiThread;

@NotNullByDefault
public interface PreviewConsumer {

	@UiThread
	void start(Camera camera, int cameraIndex);

	@UiThread
	void stop();
}
