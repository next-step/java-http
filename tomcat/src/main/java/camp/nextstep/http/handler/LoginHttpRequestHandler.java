package camp.nextstep.http.handler;

import camp.nextstep.http.domain.*;
import camp.nextstep.http.domain.response.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.model.User;
import camp.nextstep.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static camp.nextstep.http.domain.JSessionId.createJSessionId;
import static camp.nextstep.http.domain.response.HttpResponse.*;
import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class LoginHttpRequestHandler implements HttpRequestHandler {
    private final String LOGIN_PATH = "/login";
    private final String LOGIN_PAGE_PATH = "/login.html";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

    private final UserService userService;
    private final SessionHandler sessionHandler;

    public LoginHttpRequestHandler(UserService userService, SessionHandler sessionHandler) {
        this.userService = userService;
        this.sessionHandler = sessionHandler;
    }

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
            return handleLogin(requestLine);
        }

        if (existSession(requestLine)) {
            return createRedirectResponseByPath("/index.html");
        }

        StaticResource staticResource = createResourceFromPath(
                LOGIN_PAGE_PATH,
                getClass().getClassLoader()
        );

        return HttpResponse.createSuccessResponseByFile(staticResource.getResourceFile());
    }

    private boolean isLoginPageRequest(RequestLine requestLine) {
        HttpRequestStartLine httpStartLine = requestLine.getHttpStartLine();
        return httpStartLine.getMethod() == HttpMethod.GET;
    }

    private HttpResponse handleLogin(RequestLine requestLine) {
        HttpRequestBody httpRequestBody = requestLine.getHttpRequestBody();
        Map<String, String> parsedRequestBody = httpRequestBody.getFormUrlEncodedRequestBody();
        if (!isValidQueryParams(parsedRequestBody)) {
            ClassLoader classLoader = getClass().getClassLoader();
            return createBadRequestResponse(classLoader);
        }
        String account = parsedRequestBody.get(ACCOUNT);
        String password = parsedRequestBody.get(PASSWORD);
        User user = userService.findUser(account, password);

        if (user != null) {
            JSessionId jSessionId = createJSessionId();
            saveSession(jSessionId, user);
            return createRedirectResponseByPathAndSetCookie("/index.html", jSessionId.getJSessionIdStr());
        }
        return createRedirectResponseByPath("/401.html");
    }

    private void saveSession(JSessionId jSessionId, User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        sessionHandler.add(new Session(jSessionId.getJSessionId().toString(), map));
    }

    private boolean existSession(RequestLine requestLine) {
        HttpCookie httpCookie = requestLine.getHttpHeader().getHttpCookie();

        if (httpCookie == null) {
            return false;
        }

        if (httpCookie.getJsessionId() == null) {
            return false;
        }

        return sessionHandler.findSession(httpCookie.getJsessionId().getJSessionId().toString()) != null;
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
