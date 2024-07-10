package org.apache.coyote.http11.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.io.IOException;
import org.apache.coyote.http11.request.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.utils.FileUtils;

public class LoginRequestHandler implements RequestHandler {

    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";

    @Override
    public Response handle(Request request) throws IOException {
        RequestLine requestLine = request.getRequestLine();

        if (requestLine.isGet()) {
            return Response.ok(ContentType.HTML, FileUtils.getStaticFileContent(requestLine.getPath()));
        }

        if (requestLine.isPost()) {
            RequestBody requestBody = request.getRequestBody();
            String account = requestBody.getParameter(ACCOUNT_PARAMETER).toString();
            String password = requestBody.getParameter(PASSWORD_PARAMETER).toString();
            return authenticateUser(account, password);
        }

        return Response.notAllowed();
    }

    private Response authenticateUser(String account, String password) throws IOException {
        try {
            User user = findUserByAccount(account);

            if (!isPasswordValid(user, password)) {
                throw new IllegalArgumentException();
            }
            log.info(user.toString());
            return Response.redirect(FileUtils.getStaticFileContent(HttpPath.from(INDEX_PATH)));
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
