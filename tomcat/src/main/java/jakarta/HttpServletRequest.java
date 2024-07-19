package jakarta;

import org.apache.catalina.Session;
import org.apache.coyote.http11.request.MessageBody;

import java.util.Map;
import java.util.UUID;

public class HttpServletRequest {

    private Session session;
    private Cookie cookie;
    private MessageBody messageBody;

    public HttpServletRequest(Session session, Cookie cookie, MessageBody messageBody) {
        this.session = session;
        this.cookie = cookie;
        this.messageBody = messageBody;
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
}
