package org.apache.coyote.http11;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Http11Input {

    private final ByteBuffer byteBuffer;
    private final Request request = new Request();

    public Http11Input(final InputStream inputStream) {
        this.byteBuffer = toByteBuffer(inputStream);
    }

    public ByteBuffer toByteBuffer(final InputStream inputStream) {
        try {
            byte[] byteArray = readInputStreamToByteArray(inputStream);
            return ByteBuffer.wrap(byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readInputStreamToByteArray(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final byte[] temp = new byte[1024];

        int bytesRead = readChunk(inputStream, temp);

        while (bytesRead != -1) {
            buffer.write(temp, 0, bytesRead);
            bytesRead = readChunk(inputStream, temp);
        }

        return buffer.toByteArray();
    }

    private int readChunk(final InputStream inputStream, final byte[] temp) throws IOException {
        return inputStream.read(temp);
    }

    void parseRequestLine() {
        final String requestLine = readBufferLine();

        final String[] requestLineMetaData = requestLine.split(" ");

        if (requestLineMetaData.length < 3) {
            throw new RuntimeException("Invalid request line: " + requestLine);
        }

        request.setMethod(requestLineMetaData[0]);
        request.setPath(requestLineMetaData[1]);
        request.setProtocol(requestLineMetaData[2]);
    }

    private String readBufferLine() {
        final StringBuilder line = new StringBuilder();
        boolean endOfLine = false;

        while (byteBuffer.hasRemaining() && !endOfLine) {
            char c = (char) byteBuffer.get();
            endOfLine = processChar(line, c);
        }

        return line.isEmpty() && !byteBuffer.hasRemaining() ? null : line.toString();
    }

    private boolean processChar(final StringBuilder line, final char c) {
        if (c == Constants.CR || c == Constants.LF) {
            handleNewLine(c);
            return true;
        }
        line.append(c);
        return false;
    }

    private void handleNewLine(final char c) {
        if (c == Constants.CR && byteBuffer.hasRemaining() && byteBuffer.get(byteBuffer.position()) == Constants.LF) {
            byteBuffer.get();
        }
    }

    public Request getRequest() {
        return this.request;
    }
}
