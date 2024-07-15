package org.apache.coyote.http11;

import org.apache.coyote.http.Cookie;
import org.apache.coyote.http.HttpHeader;
import org.apache.coyote.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HttpInputParser {

    private static final String HEADER_SEPARATOR = ";";
    private static final String HEADER_ASSIGNMENT = ": ";
    private static final int HEADER_KEY_VALUE_NUMBER = 2;
    private static final int HEADER_LINE_NAME_POINT = 0;
    private static final int HEADER_LINE_VALUES_POINT = 1;

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
            final String[] headers = line.split(HEADER_ASSIGNMENT, HEADER_KEY_VALUE_NUMBER);

            final String[] headerValues = headers[HEADER_LINE_VALUES_POINT].split(HEADER_SEPARATOR);

            this.httpRequest.appendHeader(headers[HEADER_LINE_NAME_POINT], headerValues);
        }

        final List<String> cookies = this.httpRequest.getHeader(HttpHeader.COOKIE);

        if (cookies == null || cookies.isEmpty()) {
            return;
        }

        this.httpRequest.setCookies(cookies.stream().map(Cookie::new).toList());
    }

    private void parseBody() throws IOException {
        if (this.httpRequest.isContentLengthEmpty()) {
            return;
        }

        final int bodyLength = httpRequest.getBodyLength();

        final char[] bodyChars = new char[bodyLength];
        reader.read(bodyChars, HEADER_LINE_NAME_POINT, bodyLength);

        this.httpRequest.setBody(new String(bodyChars));
    }

    public HttpRequest getRequest() {
        return this.httpRequest;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
