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

    public Request(RequestLine requestLine,
                   RequestHeaders requestHeaders,
                   RequestCookies requestCookies,
                   RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestCookies = requestCookies;
        this.requestBody = requestBody;
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
        Session newSession = new Session(Cookie.randomJsessionId());
        SessionManager.INSTANCE.add(newSession);
        return newSession;
    }

    private String getSessionIdFromCookie() {
        Cookie cookie = getRequestCookies().get(JSESSIONID_NAME);
        if (cookie == null) return null;

        return cookie.getValue();
    }
}
