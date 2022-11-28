package org.briarproject.bramble.record;

import static org.briarproject.bramble.api.record.Record.RECORD_HEADER_BYTES;

import org.briarproject.bramble.api.record.Record;
import org.briarproject.bramble.api.record.RecordWriter;
import org.briarproject.bramble.util.ByteUtils;
import org.briarproject.nullsafety.NotNullByDefault;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@NotNullByDefault
class RecordWriterImpl implements RecordWriter {

    private final OutputStream out;
    private final byte[] header = new byte[RECORD_HEADER_BYTES];

    private long bytesWritten = 0;

    RecordWriterImpl(OutputStream out) {
        this.out = out;
    }

    @Override
    public void writeRecord(Record r) throws IOException {
        byte[] payload = r.getPayload();
        header[0] = r.getProtocolVersion();
        header[1] = r.getRecordType();
        ByteUtils.writeUint16(payload.length, header, 2);
        out.write(header);
        out.write(payload);
        bytesWritten += RECORD_HEADER_BYTES + payload.length;
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    @Override
    public long getBytesWritten() {
        return bytesWritten;
    }
}
