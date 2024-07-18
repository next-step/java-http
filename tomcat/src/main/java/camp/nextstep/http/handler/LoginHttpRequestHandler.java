package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.service.UserService;

import java.io.File;
import java.util.Map;

import static camp.nextstep.http.domain.HttpResponse.*;
import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class LoginHttpRequestHandler implements HttpRequestHandler {
    private final String LOGIN_PATH = "/login";
    private final String LOGIN_PAGE_PATH = "/login.html";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

    private final UserService userService = new UserService();

    @Override
    public boolean isExactHandler(RequestLine requestLine) {
        return requestLine.getPath().getUrlPath().startsWith(LOGIN_PATH);
    }

    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        if (!isLoginPageRequest(requestLine)) {
            return handleLogin(requestLine.getPath().getQueryParams());
        }

        StaticResource staticResource = createResourceFromPath(
                LOGIN_PAGE_PATH
        );

        return HttpResponse.createSuccessResponseByFile(staticResource.getResourceFile());
    }

    private boolean isLoginPageRequest(RequestLine requestLine) {
        return requestLine.getMethod() == HttpMethod.GET
                && (requestLine.getPath().getQueryParams() == null || requestLine.getPath().getQueryParams().isEmpty());
    }

    private HttpResponse handleLogin(Map<String, String> queryParams) {
        if (!isValidQueryParams(queryParams)) {
            return createBadRequestResponse();
        }
        String account = queryParams.get(ACCOUNT);
        String password = queryParams.get(PASSWORD);

        if (userService.isUserPresent(account, password)) {
            return createRedirectResponseByPath("/index.html");
        }
        return createRedirectResponseByPath("/401.html");
    }

    private boolean isValidQueryParams(Map<String, String> queryParams) {
        if (!queryParams.containsKey(ACCOUNT)) {
            return false;
        }

        if (!queryParams.containsKey(PASSWORD)) {
            return false;
        }

        return true;
    }
}
