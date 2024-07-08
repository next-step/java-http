package org.apache.coyote.http11;

import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;

public class HttpOutputStream implements Flushable, AutoCloseable {
    public static final byte[] LINE_SEPARATOR = System.lineSeparator().getBytes();
    private final OutputStream outputStream;

    public HttpOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(final HttpWritable httpWritable) throws IOException {
        outputStream.write(httpWritable.getBytes());
    }

    public void writeLineSeparator() throws IOException {
        outputStream.write(LINE_SEPARATOR);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws Exception {
        outputStream.close();
    }
}
