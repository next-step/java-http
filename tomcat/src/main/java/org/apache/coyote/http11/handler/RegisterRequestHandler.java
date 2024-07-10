package org.apache.coyote.http11.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.coyote.http11.request.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;
import org.apache.utils.FileUtils;

public class RegisterRequestHandler implements RequestHandler {

    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";

    @Override
    public Response handle(Request request) throws IOException {
        Session session = SessionManager.getSession(request.getSessionId());
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return Response.redirect(FileUtils.getStaticFileContent(HttpPath.from(INDEX_PATH)));
        }

        RequestLine requestLine = request.getRequestLine();
        if (requestLine.isGet()) {
            return Response.ok(ContentType.HTML, FileUtils.getStaticFileContent(requestLine.getPath()));
        }

        if (requestLine.isPost()) {
            return registerUser(request);
        }

        return Response.notAllowed();
    }

    private Response registerUser(Request request) throws IOException {
        try {
            RequestBody requestBody = request.getRequestBody();
            String account = requestBody.getParameter(ACCOUNT_PARAMETER).toString();
            String password = requestBody.getParameter(PASSWORD_PARAMETER).toString();
            String email = requestBody.getParameter(EMAIL_PARAMETER).toString();

            User user = new User(account, password, email);
            InMemoryUserRepository.save(user);
            SessionManager.getSession(request.getSessionId()).setAttribute("user", user);

            return Response.redirect(FileUtils.getStaticFileContent(HttpPath.from(INDEX_PATH)));
        } catch (IllegalArgumentException e) {
            return notFound();
        }
    }
}
