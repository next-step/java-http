package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.HttpRequest;
import camp.nextstep.domain.http.HttpResponse;
import camp.nextstep.model.User;

import java.util.Map;

public class RegisterController extends AbstractController {

    private static final String LOGIN_PAGE_PATH = "/login.html";

    private static final String REGISTER_ACCOUNT_KEY = "account";
    private static final String REGISTER_PASSWORD_KEY = "password";
    private static final String REGISTER_EMAIL_KEY = "email";

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> requestBody = httpRequest.getHttpRequestBody();
        User saveRequestUer = new User(
                requestBody.get(REGISTER_ACCOUNT_KEY),
                requestBody.get(REGISTER_PASSWORD_KEY),
                requestBody.get(REGISTER_EMAIL_KEY)
        );
        InMemoryUserRepository.save(saveRequestUer);
        return HttpResponse.found(httpRequest.getHttpProtocol(), LOGIN_PAGE_PATH);
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return handleStaticPath(httpRequest);
    }
}
