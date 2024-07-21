package camp.nextstep.http.controller;

import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.request.RequestMappingKey;
import camp.nextstep.http.domain.response.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.http.exception.DuplicateUserException;
import camp.nextstep.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class RegisterController extends AbstractController implements Controller {
    private final String REGISTER_PATH = "/register";
    private final String REGISTER_PAGE_PATH = "/register.html";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";
    private final String EMAIL = "email";

    private final RequestMappingKey REGISTER_PAGE_KEY =
            new RequestMappingKey(Pattern.compile(REGISTER_PATH), HttpMethod.GET);

    private final RequestMappingKey REGISTER_KEY =
            new RequestMappingKey(Pattern.compile(REGISTER_PATH), HttpMethod.POST);

    private final List<RequestMappingKey> requestMappingKeys =
            List.of(
                    REGISTER_PAGE_KEY,
                    REGISTER_KEY
            );

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        if (REGISTER_KEY.isRequestMatch(request)) {
            handleRegister(request, response);
        }
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (REGISTER_PAGE_KEY.isRequestMatch(request)) {
            handleRegisterPage(response);
        }
    }

    private void handleRegisterPage(HttpResponse response) {
        StaticResource staticResource = createResourceFromPath(
                REGISTER_PAGE_PATH,
                getClass().getClassLoader()
        );

        response.successResponseByFile(staticResource.getResourceFile());
    }

    private void handleRegister(HttpRequest request, HttpResponse response) {
        Map<String, String> parsedRequestBody =
                request.getHttpRequestBody().getFormUrlEncodedRequestBody();
        try {
            userService.registerUser(
                    parsedRequestBody.get(ACCOUNT),
                    parsedRequestBody.get(PASSWORD),
                    parsedRequestBody.get(EMAIL)
            );
        } catch (DuplicateUserException ex) {
            ex.printStackTrace();
            response.badRequestResponseByString();
        }
        response.redirectResponseByPath("/index.html");
    }

    @Override
    public boolean isExactHandler(HttpRequest httpRequest) {
        return requestMappingKeys.stream()
                .anyMatch(v -> v.isRequestMatch(httpRequest));
    }
}
