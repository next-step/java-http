package camp.nextstep.http.handler;

import camp.nextstep.http.domain.*;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.service.UserService;

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
        return requestLine
                .getHttpStartLine()
                .getPath()
                .getUrlPath()
                .startsWith(LOGIN_PATH);
    }

    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        if (!isLoginPageRequest(requestLine)) {
            return handleLogin(requestLine.getHttpRequestBody());
        }

        StaticResource staticResource = createResourceFromPath(
                LOGIN_PAGE_PATH,
                getClass().getClassLoader()
        );

        return HttpResponse.createSuccessResponseByFile(staticResource.getResourceFile());
    }

    private boolean isLoginPageRequest(RequestLine requestLine) {
        HttpStartLine httpStartLine = requestLine.getHttpStartLine();
        return httpStartLine.getMethod() == HttpMethod.GET;
    }

    private HttpResponse handleLogin(HttpRequestBody httpRequestBody) {
        Map<String, String> parsedRequestBody = httpRequestBody.getFormUrlEncodedRequestBody();
        if (!isValidQueryParams(parsedRequestBody)) {
            ClassLoader classLoader = getClass().getClassLoader();
            return createBadRequestResponse(classLoader);
        }
        String account = parsedRequestBody.get(ACCOUNT);
        String password = parsedRequestBody.get(PASSWORD);

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
