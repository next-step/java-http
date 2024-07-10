package org.apache.coyote.http11;

import org.apache.coyote.http.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpInputParser {

    private final Request request = new Request();
    private final BufferedReader reader;

    public HttpInputParser(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));;
    }

    void parseRequestLine() throws IOException, HttpParseException {
        request.setRequestLine(reader.readLine());
    }

    public Request getRequest() {
        return this.request;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
