package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.session.SessionService;
import camp.nextstep.model.User;
import camp.nextstep.request.HttpQueryParameters;
import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;

import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class LoginController extends AbstractController {

    private final SessionService sessionService;

    public LoginController() {
        this.sessionService = new SessionService();
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        if (sessionService.isLoggedIn(request.getSession())) {
            response.redirectTo("/index.html");
            return;
        }

        response.render("/login.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        final HttpQueryParameters requestBody = request.getRequestBody().toQueryParameters();

        final String account = requireNonNull(requestBody.getString("account"));
        final String password = requireNonNull(requestBody.getString("password"));

        Optional<User> user = InMemoryUserRepository.findByAccount(account);
        Boolean found = user
                .map(acc -> acc.checkPassword(password))
                .orElse(false);
        if (!found) {
            response.redirectTo("/401.html");
            return;
        }

        sessionService.signInAs(request.getSession(), user.get());

        response.redirectTo("/index.html");
    }
}
