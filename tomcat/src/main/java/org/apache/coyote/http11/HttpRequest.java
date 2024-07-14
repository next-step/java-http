package org.apache.coyote.http11;

import org.apache.session.Session;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class HttpRequest {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int HEADER_START_INDEX = 1;
    private static final String CRLF = "\r\n";
    private static final String EMPTY = "";

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;
    private final Cookies cookies;
    private Session session;

    private HttpRequest(final RequestLine requestLine, final HttpHeaders httpHeaders, final RequestBody requestBody, final Cookies cookies) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
        this.cookies = cookies;
    }

    public static HttpRequest from(final String httpRequestMessage) {
        String[] httpRequestMessages = httpRequestMessage.split(CRLF);
        List<HttpHeader> httpHeaders = parseHttpHeaders(httpRequestMessages);
        String cookies = findCookies(httpHeaders);

        return new HttpRequest(
                RequestLine.from(httpRequestMessages[REQUEST_LINE_INDEX]),
                HttpHeaders.from(httpHeaders),
                RequestBody.empty(),
                Cookies.from(cookies)
        );
    }

    private static List<HttpHeader> parseHttpHeaders(final String[] httpRequestMessages) {
        return Arrays.stream(httpRequestMessages, HEADER_START_INDEX, httpRequestMessages.length)
                .takeWhile(Predicate.not(String::isBlank))
                .map(HttpHeader::from)
                .toList();
    }

    private static String findCookies(final List<HttpHeader> httpHeaders) {
        return httpHeaders.stream()
                .filter(httpHeader -> httpHeader.equalsName(HttpHeaderName.COOKIE.getValue()))
                .findAny()
                .map(HttpHeader::getValue)
                .orElse(EMPTY);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpProtocol getProtocol() {
        return requestLine.getProtocol();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public String getHeaderValue(final String name) {
        return httpHeaders.getHeaderValue(name);
    }

    public void addRequestBody(final String body) {
        requestBody.addBody(body);
    }

    public String getBodyValue(final String key) {
        return requestBody.getValue(key);
    }

    public Cookie getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.equalsName(name))
                .findAny()
                .orElse(null);
    }

    public void setSession(final Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}