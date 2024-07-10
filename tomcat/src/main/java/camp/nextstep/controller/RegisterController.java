package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;
import camp.nextstep.model.User;

public class RegisterController extends AbstractController {

    private static final String LOGIN_PAGE_PATH = "/login.html";

    private static final String REGISTER_ACCOUNT_KEY = "account";
    private static final String REGISTER_PASSWORD_KEY = "password";
    private static final String REGISTER_EMAIL_KEY = "email";

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        User saveRequestUer = new User(
                httpRequest.getHttpRequestBodyValue(REGISTER_ACCOUNT_KEY),
                httpRequest.getHttpRequestBodyValue(REGISTER_PASSWORD_KEY),
                httpRequest.getHttpRequestBodyValue(REGISTER_EMAIL_KEY)
        );
        InMemoryUserRepository.save(saveRequestUer);
        return HttpResponse.found(httpRequest.getHttpProtocol(), LOGIN_PAGE_PATH);
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return handleStaticPath(httpRequest);
    }
}
