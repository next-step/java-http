package camp.nextstep.request;

import org.apache.catalina.Session;
import org.apache.coyote.http11.SessionManager;

import java.io.IOException;

import static camp.nextstep.request.HttpRequestCookie.JSESSIONID_NAME;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final HttpRequestCookies cookies;
    private final HttpRequestBody requestBody;

    public HttpRequest(HttpRequestLine requestLine,
                       HttpRequestHeaders requestHeaders,
                       HttpRequestCookies cookies,
                       HttpRequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.cookies = cookies;
        this.requestBody = requestBody;
    }

    public boolean isGET() {
        return requestLine.getMethod() == HttpRequestMethod.GET;
    }

    public boolean isPOST() {
        return requestLine.getMethod() == HttpRequestMethod.POST;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpQueryParameters getQueryParameters() {
        return requestLine.getQueryParameters();
    }

    public HttpRequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public HttpRequestCookies getCookies() {
        return cookies;
    }

    /**
     * 요청에서 세션을 가지고 있었는지 확인
     */
    public boolean hasSession() {
        return SessionManager.INSTANCE.findSession(getSessionIdFromCookie()) != null;
    }

    /**
     * 쿠키에 있는 세션 ID 로 조회되는 세션을 리턴합니다. 없으면 신규 생성해서 리턴합니다.
     * <ul>
     *     <li>쿠키에 세션 id 지정이 안돼있는 경우 신규 생성</li>
     *     <li>저장돼있는 세션 id 가 실제 세션 매니저에 없을때 신규 생성</li>
     * </ul>
     *
     * @return Session 객체
     */
    public Session getSession() throws IOException {
        Session session = SessionManager.INSTANCE.findSession(getSessionIdFromCookie());
        if (session != null) {
            return session;
        }
        Session newSession = new Session(HttpRequestCookie.randomJsessionId());
        SessionManager.INSTANCE.add(newSession);
        return newSession;
    }

    private String getSessionIdFromCookie() {
        HttpRequestCookie cookie = getCookies().get(JSESSIONID_NAME);
        if (cookie == null) return null;

        return cookie.getValue();
    }
}
