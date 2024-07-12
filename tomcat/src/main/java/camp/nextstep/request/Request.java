package camp.nextstep.request;

import org.apache.catalina.Session;
import org.apache.coyote.http11.SessionManager;

import java.io.IOException;

import static camp.nextstep.request.Cookie.JSESSIONID_NAME;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestCookies requestCookies;
    private final RequestBody requestBody;
    private Session newSession;

    public Request(RequestLine requestLine,
                   RequestHeaders requestHeaders,
                   RequestCookies requestCookies,
                   RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestCookies = requestCookies;
        this.requestBody = requestBody;

        this.newSession = null;
    }

    public boolean isGET() {
        return requestLine.getMethod() == RequestMethod.GET;
    }

    public boolean isPOST() {
        return requestLine.getMethod() == RequestMethod.POST;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public RequestCookies getRequestCookies() {
        return requestCookies;
    }

    /**
     * 쿠키에 있는 세션 ID 로 조회되는 세션을 리턴합니다.
     *
     * @param sessionManager 세션 조회에 사용할 세션 매니저
     * @param createNew      세션이 없을 때 createNew 인 경우는 새로 생성하여 리턴하고, createNew 가 false 인 경우는 null 을 리턴한다.
     * @return Session 객체
     */
    public Session getSession(SessionManager sessionManager, boolean createNew) throws IOException {
        if (!createNew) return sessionManager.findSession(getSessionIdFromCookie());

        if (newSession != null) return newSession;

        Session currentSessionOrNull = sessionManager.findSession(getSessionIdFromCookie());
        if (currentSessionOrNull != null) return currentSessionOrNull;

        newSession = new Session(Cookie.randomJsessionId());
        sessionManager.add(newSession);
        return newSession;
    }

    private String getSessionIdFromCookie() {
        Cookie cookie = getRequestCookies().get(JSESSIONID_NAME);
        if (cookie == null) return null;

        return cookie.getValue();
    }
}
