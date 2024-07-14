package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.coyote.AbstractController;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.file.ResourceHandler;
import org.apache.http.HttpPath;
import org.apache.http.session.HttpCookie;

import java.io.IOException;

public class LoginController extends AbstractController {
    private static final String MAPPING_PATH = "/login";
    private static final String SUCCESS_PAGE = "/index.html";
    private static final String FAIL_PAGE = "/401.html";

    public LoginController() {
        super(MAPPING_PATH);
    }

    @Override
    public HttpResponse doPost(final HttpRequest request) {
        var loginRequest = new LoginRequest(request);
        return login(request, loginRequest);
    }

    @Override
    public HttpResponse doGet(final HttpRequest request) throws IOException {
        var session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return new HttpResponse(new HttpPath(SUCCESS_PAGE));
        }

        var loginPage = request.path() + ".html";
        return ResourceHandler.handle(loginPage);
    }

    private HttpResponse login(final HttpRequest httpRequest, final LoginRequest request) {
        var user = InMemoryUserRepository.findByAccount(request.account()).orElse(null);
        if (user == null || !user.checkPassword(request.password())) {
            return new HttpResponse(new HttpPath(FAIL_PAGE));
        }

        var session = httpRequest.getSession(true);
        session.setAttribute("user", user);
        return new HttpResponse(new HttpPath(SUCCESS_PAGE)).cookie(HttpCookie.ofSession(session));
    }
}
