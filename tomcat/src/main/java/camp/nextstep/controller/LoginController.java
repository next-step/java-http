package camp.nextstep.controller;

import camp.nextstep.exception.UnauthorizedException;
import camp.nextstep.exception.UserNotFoundException;
import camp.nextstep.service.LoginService;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;

public class LoginController extends AbstractController {

    private static final String INDEX_PATH = "/index.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";
    private static final String NOT_FOUND_PATH = "/404.html";


    @Override
    protected void doGet(Request request, Response response) throws Exception {

        LoginService loginService = new LoginService();
        if (loginService.isLoginUser(request)) {
            response.found(INDEX_PATH);
            return;
        }
        super.doGet(request, response);
    }

    @Override
    protected void doPost(Request request, Response response) throws Exception {
        LoginService loginService = new LoginService();
        try {
            loginService.authenticateUser(request);

        } catch (UserNotFoundException e) {
            response.notFound(ContentType.HTML, NOT_FOUND_PATH);
        } catch (UnauthorizedException e) {
            response.unauthorized(UNAUTHORIZED_PATH);
        }
    }
}
