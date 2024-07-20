package jakarta;

import org.apache.catalina.Session;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;
import org.apache.coyote.http11.response.HttpStatusCode;
import org.apache.coyote.http11.response.MessageBody;

public class HttpServletResponse {

    private Session session;
    private ViewModel viewModel;
    private HttpStatusCode statusCode;
    private String redirectPath;
    private MimeType mimeType;
    private Cookie cookie;
    private MessageBody messageBody;

    public HttpServletResponse() {
    }

    public void setResponseBody(MessageBody messageBody) {
       this.messageBody = messageBody;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setRedirectPath(String path) {
        this.redirectPath = path;
    }

    public void addCookie(HttpCookie httpCookie) {
        this.cookie = Cookie.from(httpCookie);
    }

    public String getJsessionId() {
        return cookie.getValue(HttpCookie.JSESSIONID);
    }


    public HttpStatusCode getStatusCode() {
        return statusCode;
    }


    public Session getSession() {
        return session;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

}
