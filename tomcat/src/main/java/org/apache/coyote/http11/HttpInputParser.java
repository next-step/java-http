package org.apache.coyote.http11;

import org.apache.coyote.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpInputParser {

    private final HttpRequest httpRequest = new HttpRequest();
    private final BufferedReader reader;

    public HttpInputParser(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));;
    }

    void parseRequestLine() throws IOException, HttpParseException {
        httpRequest.setRequestLine(reader.readLine());
    }

    public HttpRequest getRequest() {
        return this.httpRequest;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
