package org.apache.coyote.http11;

import org.apache.coyote.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpInputParser {

    public static final int REQUEST_LINE_NUMBERS = 3;

    private final Request request = new Request();

    void parseRequestLine(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final String requestLine = reader.readLine();

        final String[] requestLineMetaData = requestLine.split(" ");

        if (requestLineMetaData.length < REQUEST_LINE_NUMBERS) {
            throw new RuntimeException("Invalid request line: " + requestLine);
        }

        request.setMethod(requestLineMetaData[0]);
        request.setPath(requestLineMetaData[1]);
        request.setProtocol(requestLineMetaData[2]);

        reader.close();
    }

    public Request getRequest() {
        return this.request;
    }
}
