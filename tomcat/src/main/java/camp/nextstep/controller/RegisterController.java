package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.http.domain.ContentType;
import camp.nextstep.http.domain.HttpRequest;
import camp.nextstep.http.domain.HttpResponse;
import camp.nextstep.http.domain.RequestBody;
import camp.nextstep.http.domain.Route;
import camp.nextstep.model.User;

import java.io.IOException;

public class RegisterController extends AbstractController {
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        response.setContentType(ContentType.HTML);
        response.forward(Route.REGISTER);
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        final RequestBody requestBody = request.getRequestBody();
        InMemoryUserRepository.save(
                new User(requestBody.get(ACCOUNT), requestBody.get(PASSWORD), requestBody.get(EMAIL))
        );
        response.sendRedirect(Route.INDEX);
    }
}
