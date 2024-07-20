package jakarta;

import org.apache.catalina.Session;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.MessageBody;
import org.apache.coyote.http11.request.RequestLine;

import java.util.UUID;

public class HttpServletRequest {

    private RequestLine requestLine;
    private Session session;
    private Cookie cookie;
    private MessageBody messageBody;

    public HttpServletRequest(RequestLine requestLine, Session session, Cookie cookie, MessageBody messageBody) {
        this.requestLine = requestLine;
        this.session = session;
        this.cookie = cookie;
        this.messageBody = messageBody;
    }

    public static HttpServletRequest from(HttpRequest httpRequest) {
        HttpCookie httpCookie = httpRequest.getRequestHeaders().getCookie();
        return new HttpServletRequest(httpRequest.getRequestLine(), httpRequest.getSession(), Cookie.from(httpCookie), httpRequest.getMessageBody());
    }

    public Session getSession() {
        return session;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public Session getSession(boolean create) {
        if (create) {
            this.session = new Session(UUID.randomUUID().toString());
        }
        return this.session;
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
