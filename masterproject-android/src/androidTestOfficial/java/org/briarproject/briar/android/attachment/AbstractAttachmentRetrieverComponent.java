package org.briarproject.masterproject.android.attachment;

import org.briarproject.masterproject.android.attachment.media.MediaModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MediaModule.class
})
interface AbstractAttachmentRetrieverComponent {

    void inject(AttachmentRetrieverIntegrationTest test);

}
