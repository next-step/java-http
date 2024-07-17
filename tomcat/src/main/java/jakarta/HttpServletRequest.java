package jakarta;

import org.apache.catalina.Cookie;
import org.apache.catalina.Session;

import java.util.Map;
import java.util.UUID;

public class HttpServletRequest {

    private Session session;
    private Cookie cookie;
    private Map<String, Object> requestBody;

    public HttpServletRequest(Session session, Cookie cookie, Map<String, Object> requestBody) {
        this.session = session;
        this.cookie = cookie;
        this.requestBody = requestBody;
    }

    public Session getSession() {
        return session;
    }


    public Map<String, Object> getRequestBody() {
        return requestBody;
    }

    public Session getSession(boolean create) {
        if (create) {
            this.session = new Session(UUID.randomUUID().toString());
        }
        return this.session;
    }
}
