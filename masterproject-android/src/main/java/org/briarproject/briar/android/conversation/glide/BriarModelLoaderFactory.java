package org.briarproject.masterproject.android.conversation.glide;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import org.briarproject.masterproject.android.BriarApplication;
import org.briarproject.masterproject.api.attachment.AttachmentHeader;
import org.briarproject.nullsafety.NotNullByDefault;

import java.io.InputStream;

@NotNullByDefault
class BriarModelLoaderFactory
        implements ModelLoaderFactory<AttachmentHeader, InputStream> {

    private final BriarApplication app;

    BriarModelLoaderFactory(BriarApplication app) {
        this.app = app;
    }

    @Override
    public ModelLoader<AttachmentHeader, InputStream> build(
            MultiModelLoaderFactory multiFactory) {
        return new BriarModelLoader(app);
    }

    @Override
    public void teardown() {
        // noop
    }

}
