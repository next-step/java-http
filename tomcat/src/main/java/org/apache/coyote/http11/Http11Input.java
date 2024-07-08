package org.apache.coyote.http11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Http11Input {

    private final BufferedReader reader;

    private final Request request = new Request();

    public Http11Input(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    void parseRequestLine() throws IOException {
        final String requestLine = reader.readLine();

        final String[] requestLineMetaData = requestLine.split(" ");

        if (requestLineMetaData.length < 3) {
            throw new RuntimeException("Invalid request line: " + requestLine);
        }

        request.setMethod(requestLineMetaData[0]);
        request.setPath(requestLineMetaData[1]);
        request.setProtocol(requestLineMetaData[2]);
    }

    public Request getRequest() {
        return this.request;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
