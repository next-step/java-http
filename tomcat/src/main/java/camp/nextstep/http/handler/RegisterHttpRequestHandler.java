package camp.nextstep.http.handler;

import camp.nextstep.http.domain.HttpResponse;
import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.service.UserService;

import java.util.Map;

import static camp.nextstep.http.domain.HttpResponse.createRedirectResponseByPath;
import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class RegisterHttpRequestHandler implements HttpRequestHandler {
    private final String REGISTER_PATH = "/register";
    private final String LOGIN_PAGE_PATH = "/register.html";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

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
            return handleRegister(requestLine.getHttpStartLine().getPath().getQueryParams());
        }

        StaticResource staticResource = createResourceFromPath(
                LOGIN_PAGE_PATH,
                getClass().getClassLoader()
        );

        return HttpResponse.createSuccessResponseByFile(staticResource.getResourceFile());
    }

    private boolean isRegisterPageRequest(RequestLine requestLine) {
        return requestLine.getHttpStartLine().getMethod() == HttpMethod.GET;
    }

    private HttpResponse handleRegister(Map<String, String> queryParams) {
//        if (!isValidQueryParams(queryParams)) {
//            return createBadRequestResponse();
//        }
//        String account = queryParams.get(ACCOUNT);
//        String password = queryParams.get(PASSWORD);
//
//        if (userService.isUserPresent(account, password)) {
//            return createRedirectResponseByPath("/index.html");
//        }
//        return createRedirectResponseByPath("/401.html");
        return createRedirectResponseByPath("/index.html");
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
