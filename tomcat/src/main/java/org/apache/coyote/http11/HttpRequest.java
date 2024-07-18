package org.apache.coyote.http11;

import org.apache.session.Session;
import org.apache.session.SessionManager;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class HttpRequest {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int HEADER_START_INDEX = 1;
    private static final String CRLF = "\r\n";
    private static final String EMPTY = "";

    private final SessionManager sessionManager;

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;
    private final Cookies cookies;
    private Session session;

    private HttpRequest(final SessionManager sessionManager, final RequestLine requestLine, final HttpHeaders httpHeaders, final RequestBody requestBody, final Cookies cookies, final Session session) {
        this.sessionManager = sessionManager;
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
        this.cookies = cookies;
        this.session = session;
    }

    public static HttpRequest of(final String httpRequestMessage, final SessionManager sessionManager) {
        String[] httpRequestMessages = httpRequestMessage.split(CRLF);
        List<HttpHeader> httpHeaders = parseHttpHeaders(httpRequestMessages);
        Cookies cookies = Cookies.from(findCookies(httpHeaders));
        Session session = findSession(sessionManager, cookies);

        return new HttpRequest(
                sessionManager,
                RequestLine.from(httpRequestMessages[REQUEST_LINE_INDEX]),
                HttpHeaders.from(httpHeaders),
                RequestBody.empty(),
                cookies,
                session
        );
    }

    private static Session findSession(final SessionManager sessionManager, final Cookies cookies) {
        Cookie jSessionCookie = cookies.findJSessionCookie();
        return sessionManager.findSession(jSessionCookie.getValue());
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

    public Session getSession() {
        return getSession(false);
    }

    /**
     * 세션을 얻을 수 있습니다.
     * @param requiresNew 세션이 없을 경우 새로 생성할 것인지 여부
     * @return requiresNew가 true일 경우 세션이 없으면 새로 생성되고, requiresNew가 false일 경우 세션이 없으면 null이 반환됩니다.
     */
    public Session getSession(boolean requiresNew) {
        if (session == null && requiresNew) {
            Session newSession = sessionManager.createSession();
            sessionManager.add(newSession);
            session = newSession;
            return newSession;
        }

        return session;
    }

    public boolean hasSession() {
        return session != null;
    }
}