package org.briarproject.masterproject.android.attachment;

import static org.briarproject.masterproject.android.attachment.AttachmentDimensions.getAttachmentDimensions;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AttachmentModule {

    @Provides
    AttachmentDimensions provideAttachmentDimensions(Application app) {
        return getAttachmentDimensions(app.getResources());
    }

    @Provides
    @Singleton
    AttachmentRetriever provideAttachmentRetriever(
            AttachmentRetrieverImpl attachmentRetriever) {
        return attachmentRetriever;
    }

    @Provides
    @Singleton
    AttachmentCreator provideAttachmentCreator(
            AttachmentCreatorImpl attachmentCreator) {
        return attachmentCreator;
    }
}
