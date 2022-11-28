package org.briarproject.masterproject.android.attachment.media;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

public abstract class AbstractImageCompressorTest {

    @Inject
    ImageCompressor imageCompressor;

    public AbstractImageCompressorTest() {
        AbstractImageCompressorComponent component =
                DaggerAbstractImageCompressorComponent.builder().build();
        component.inject(this);
    }

    static AssetManager getAssetManager() {
        // pm.getResourcesForApplication(packageName).getAssets() did not work
        //noinspection deprecation
        return getContext().getAssets();
    }

    protected abstract void inject(
            AbstractImageCompressorComponent component);

    void testCompress(String filename, String contentType)
            throws IOException {
        InputStream is = getAssetManager().open(filename);
        imageCompressor.compressImage(is, contentType);
    }

}
