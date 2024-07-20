package org.apache.coyote;


import org.apache.catalina.Session;
import org.apache.catalina.SessionManager;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.request.HttpRequest;

import java.io.IOException;

public class CoyoteAdapter {

    private final SessionManager sessionManager = SessionManager.INSTANCE;

    public void parseSessionCookieId(HttpRequest request) throws IOException {
        final var cookie = request.getRequestHeaders().getCookie();
        if (cookie != null && cookie.contains(HttpCookie.JSESSIONID)) {
            String sessionId = cookie.getSessionId();
            Session session = sessionManager.findSession(sessionId);
            if (session == null) {
                session = new Session(sessionId);
                sessionManager.add(session);
            }
            request.setSession(session);
        }
    }
}
