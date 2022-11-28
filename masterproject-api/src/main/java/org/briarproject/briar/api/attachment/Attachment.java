package org.briarproject.masterproject.api.attachment;

import org.briarproject.nullsafety.NotNullByDefault;

import java.io.InputStream;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class Attachment {

	private final AttachmentHeader header;
	private final InputStream stream;

	public Attachment(AttachmentHeader header, InputStream stream) {
		this.header = header;
		this.stream = stream;
	}

	public AttachmentHeader getHeader() {
		return header;
	}

	public InputStream getStream() {
		return stream;
	}

}
