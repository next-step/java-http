package camp.nextstep.http.handler;

import camp.nextstep.http.domain.HttpRequestBody;
import camp.nextstep.http.domain.HttpResponse;
import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.http.exception.DuplicateUserException;
import camp.nextstep.service.UserService;

import java.util.Map;

import static camp.nextstep.http.domain.HttpResponse.createBadRequestResponseByString;
import static camp.nextstep.http.domain.HttpResponse.createRedirectResponseByPath;
import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class RegisterHttpRequestHandler implements HttpRequestHandler {
    private final String REGISTER_PATH = "/register";
    private final String REGISTER_PAGE_PATH = "/register.html";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";
    private final String EMAIL = "email";

    private final UserService userService = new UserService();

    @Override
    public boolean isExactHandler(RequestLine requestLine) {
        return requestLine
                .getHttpStartLine()
                .getPath()
                .getUrlPath()
                .startsWith(REGISTER_PATH);
    }

    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        if (!isRegisterPageRequest(requestLine)) {
            return handleRegister(requestLine.getHttpRequestBody());
        }

        StaticResource staticResource = createResourceFromPath(
                REGISTER_PAGE_PATH,
                getClass().getClassLoader()
        );

        return HttpResponse.createSuccessResponseByFile(staticResource.getResourceFile());
    }

    private boolean isRegisterPageRequest(RequestLine requestLine) {
        return requestLine.getHttpStartLine().getMethod() == HttpMethod.GET;
    }

    private HttpResponse handleRegister(HttpRequestBody httpRequestBody) {
        Map<String, String> parsedRequestBody = httpRequestBody.getFormUrlEncodedRequestBody();
        try {
            userService.registerUser(
                    parsedRequestBody.get(ACCOUNT),
                    parsedRequestBody.get(PASSWORD),
                    parsedRequestBody.get(EMAIL)
            );
        } catch (DuplicateUserException ex) {
            ex.printStackTrace();
            return createBadRequestResponseByString();
        }
        return createRedirectResponseByPath("/index.html");
    }
}
