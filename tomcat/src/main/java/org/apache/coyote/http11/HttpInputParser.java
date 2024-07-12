package org.apache.coyote.http11;

import org.apache.coyote.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpInputParser {

    private static final String HEADER_SEPARATOR = ";";
    private static final String HEADER_ASSIGNMENT = ": ";
    public static final int HEADER_KEY_VALUE_NUMBER = 2;

    private final HttpRequest httpRequest = new HttpRequest();
    private final BufferedReader reader;

    public HttpInputParser(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));;
    }

    public void parseRequest() throws IOException, HttpParseException {
        parseRequestLine();
        parseHeader();
        parseBody();
    }

    private void parseRequestLine() throws IOException, HttpParseException {
        final String requestLine = reader.readLine();
        httpRequest.setRequestLine(requestLine);
    }

    private void parseHeader() throws IOException {
        String line;
        while ((line = this.reader.readLine()) != null && !line.isEmpty()) {
            final String[] header = line.split(HEADER_ASSIGNMENT, HEADER_KEY_VALUE_NUMBER);

            if (header.length != HEADER_KEY_VALUE_NUMBER) {
                return;
            }

            final String[] headerValues = header[1].split(HEADER_SEPARATOR);

            this.httpRequest.appendHeader(header[0], headerValues);
        }
    }

    private void parseBody() throws IOException {
        if (this.httpRequest.isContentLengthEmpty()) {
            return;
        }

        final int bodyLength = httpRequest.getBodyLength();

        final char[] bodyChars = new char[bodyLength];
        reader.read(bodyChars, 0, bodyLength);

        this.httpRequest.setBody(new String(bodyChars));
    }

    public HttpRequest getRequest() {
        return this.httpRequest;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
