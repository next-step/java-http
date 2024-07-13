package org.apache.coyote.http11.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.io.IOException;
import org.apache.coyote.http11.meta.HttpCookie;
import org.apache.coyote.http11.meta.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;
import org.apache.utils.FileUtils;

public class LoginRequestHandler implements RequestHandler {

    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_KEY = "user";

    @Override
    public Response handle(Request request) throws IOException {
        Session session = SessionManager.getSession(request.getCookies());
        User user = (User) session.getAttribute(USER_KEY);
        if (user != null) {
            return Response.found(INDEX_PATH);
        }

        RequestLine requestLine = request.getRequestLine();

        if (requestLine.isGet()) {
            return Response.ok(ContentType.HTML, FileUtils.getStaticFileContent(requestLine.getPath()));
        }

        if (requestLine.isPost()) {
            return authenticateUser(request);
        }

        return Response.notAllowed();
    }

    private Response authenticateUser(Request request) throws IOException {
        try {
            RequestBody requestBody = request.getRequestBody();
            String account = requestBody.getParameter(ACCOUNT_PARAMETER).toString();
            String password = requestBody.getParameter(PASSWORD_PARAMETER).toString();

            User user = findUserByAccount(account);

            if (!isPasswordValid(user, password)) {
                throw new IllegalArgumentException();
            }

            HttpCookie httpCookie = request.getCookies();
            Session session = SessionManager.getSession(httpCookie);
            session.setAttribute(USER_KEY, user);

            return Response.found(httpCookie, INDEX_PATH);
        } catch (IllegalArgumentException e) {
            return Response.unauthorized(FileUtils.getStaticFileContent(HttpPath.from(UNAUTHORIZED_PATH)));
        }
    }

    private User findUserByAccount(String account) {
        return InMemoryUserRepository.findByAccount(account)
            .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.checkPassword(password);
    }
}
