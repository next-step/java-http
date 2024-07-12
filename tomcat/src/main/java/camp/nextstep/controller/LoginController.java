package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import camp.nextstep.request.HttpQueryParameters;
import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (request.isLoggedIn()) {
            response.redirectTo("/index.html");
            return;
        }

        response.renderStaticResource("/login.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
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

        request.signInAs(user.get());

        response.redirectTo("/index.html");
    }
}
