package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import camp.nextstep.request.HttpQueryParameters;
import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;
import camp.nextstep.session.SessionService;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class RegisterController extends AbstractController {
    private final SessionService sessionService;

    public RegisterController() {
        this.sessionService = new SessionService();
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.render("/register.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        final HttpQueryParameters requestBody = request.getRequestBody().toQueryParameters();

        final String account = requireNonNull(requestBody.getString("account"));
        final String email = requireNonNull(requestBody.getString("email"));
        final String password = requireNonNull(requestBody.getString("password"));

        User user = new User(account, password, email);
        InMemoryUserRepository.save(user);

        sessionService.signInAs(request.getSession(), user);

        response.redirectTo("/index.html");
    }
}
