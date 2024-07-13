package org.apache.coyote.http;

import org.apache.coyote.http11.HttpParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {

    private static final String RESPONSE_LINE_SEPARATOR = " ";
    private static final int RESPONSE_LINE_NUMBERS = 3;
    private static final int REQUEST_LINE_HTTP_VERSION_POINT = 0;
    private static final int REQUEST_LINE_STATUS_CODE_POINT = 1;
    private static final String COOKIE_SEPARATOR = "; ";

    private final HttpResponseLine httpResponseLine = new HttpResponseLine();
    private final HeaderMapping headerMapping = new HeaderMapping();
    private final Cookies cookies = new Cookies();
    private final StringBuilder body = new StringBuilder();

    public void init() {
        this.httpResponseLine.setHttpVersion(HttpVersion.HTTP1_1);
        this.httpResponseLine.setStatusCode(StatusCode.OK);
    }

    public void setResponseLine(final HttpVersion httpVersion, final StatusCode statusCode) {
        this.httpResponseLine.setHttpVersion(httpVersion);
        setStatusCode(statusCode);
    }

    public void setResponseLine(final String responseLine) throws HttpParseException {
        final String[] responseLineMetaData = responseLine.split(RESPONSE_LINE_SEPARATOR);

        if (responseLineMetaData.length < RESPONSE_LINE_NUMBERS) {
            throw new HttpParseException("Invalid response line: " + responseLine);
        }

        this.httpResponseLine.setHttpVersion(HttpVersion.from(responseLineMetaData[REQUEST_LINE_HTTP_VERSION_POINT]));
        this.httpResponseLine.setStatusCode(StatusCode.from(Integer.parseInt(responseLineMetaData[REQUEST_LINE_STATUS_CODE_POINT])));
    }

    public void setStatusCode(final StatusCode statusCode) {
        this.httpResponseLine.setStatusCode(statusCode);
    }

    public void setBody(final String body) {
        setBody(body, ContentType.TEXT_PLAIN);
    }

    public void setBody(final String body, final ContentType mimeType) {
        this.body.setLength(0);
        this.body.append(body);
        this.headerMapping.addHeader(HttpHeader.CONTENT_TYPE, mimeType.type(), ContentType.CHARSET_UTF_8.type());
        this.headerMapping.addHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(this.body.length()));
    }

    public void addCookie(final Cookie... cookie) {
        this.cookies.addCookie(cookie);

        final String cookieLine = Arrays.stream(cookie).map(Cookie::toCookieLine).collect(Collectors.joining(COOKIE_SEPARATOR));
        this.headerMapping.addHeader(HttpHeader.SET_COOKIE, cookieLine);
    }

    public byte[] toBytes() {
        return String.join(System.lineSeparator(),
                httpResponseLine.toResponseLine() + HeaderMapping.HEADER_SPACE,
                headerMapping.convertHttpHeaders(),
                "",
                body.toString()).getBytes();
    }

    public void addHeader(final HttpHeader header, final String... value) {
        this.headerMapping.addHeader(header, value);
    }

    public String responseLine() {
        return this.httpResponseLine.toResponseLine();
    }

    public String headers() {
        return this.headerMapping.convertHttpHeaders();
    }

    public String body() {
        return this.body.toString();
    }
}

