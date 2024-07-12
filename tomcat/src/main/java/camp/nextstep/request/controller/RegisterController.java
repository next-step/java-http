package camp.nextstep.request.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.model.QueryParams;

public class RegisterController extends AbstractController {
    private static final String URL = "/register.html";
    private static final String POST_METHOD_REDIRECT_PATH = "/index.html";
    private static final String OTHER_METHOD_REDIRECT_PATH = "/404.html";
    private static final String ACCOUNT_KEY = "account";
    private static final String PASSWORD_KEY = "password";
    private static final String EMAIL_KEY = "email";

    private RegisterController() {
    }

    public static RegisterController getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public String url() {
        return URL;
    }

    @Override
    protected HttpResponse doGet(HttpRequest request) throws Exception {
        return buildOkHttpResponse(request);
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        if (request.hasRequestBody()) {
            saveUser(request.requestBody());
            return buildRedirectHttpResponse(request, POST_METHOD_REDIRECT_PATH);
        }

        return buildRedirectHttpResponse(request, OTHER_METHOD_REDIRECT_PATH);
    }

    private void saveUser(final QueryParams requestBody) {
        InMemoryUserRepository.save(
                new User(
                        requestBody.valueBy(ACCOUNT_KEY),
                        requestBody.valueBy(PASSWORD_KEY),
                        requestBody.valueBy(EMAIL_KEY)
                )
        );
    }

    private static class SingletonHelper {
        private static final RegisterController INSTANCE = new RegisterController();
    }
}
