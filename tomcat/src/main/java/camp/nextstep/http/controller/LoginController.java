package camp.nextstep.http.controller;

import camp.nextstep.http.domain.HttpCookie;
import camp.nextstep.http.domain.JSessionId;
import camp.nextstep.http.domain.Session;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.request.HttpRequestBody;
import camp.nextstep.http.domain.request.RequestMappingKey;
import camp.nextstep.http.domain.response.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.http.handler.SessionHandler;
import camp.nextstep.model.User;
import camp.nextstep.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static camp.nextstep.http.domain.JSessionId.createJSessionId;
import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class LoginController extends AbstractController implements Controller {
    private final String LOGIN_PAGE_PATH = "/login.html";
    private final String LOGIN_PATH = "/login";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

    private final RequestMappingKey LOGIN_PAGE_KEY =
            new RequestMappingKey(Pattern.compile(LOGIN_PATH), HttpMethod.GET);

    private final RequestMappingKey LOGIN_KEY =
            new RequestMappingKey(Pattern.compile(LOGIN_PATH), HttpMethod.POST);

    private final List<RequestMappingKey> requestMappingKeys =
            List.of(
                    LOGIN_PAGE_KEY,
                    LOGIN_KEY
            );

    private SessionHandler sessionHandler;
    private UserService userService;

    public LoginController(SessionHandler sessionHandler, UserService userService) {
        this.sessionHandler = sessionHandler;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        if (LOGIN_KEY.isRequestMatch(request)) {
            handleLogin(request, response);
        }
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (LOGIN_PAGE_KEY.isRequestMatch(request)) {
            handleLoginPage(request, response);
        }
    }

    public boolean isExactHandler(HttpRequest httpRequest) {
        return requestMappingKeys.stream()
                .anyMatch(v -> v.isRequestMatch(httpRequest));
    }

    private void handleLoginPage(HttpRequest request, HttpResponse response) {
        if (existSession(request)) {
            response.redirectResponseByPath("/index.html");
        }

        StaticResource staticResource = createResourceFromPath(
                LOGIN_PAGE_PATH,
                getClass().getClassLoader()
        );

        response.successResponseByFile(staticResource.getResourceFile());
    }

    private void handleLogin(HttpRequest request, HttpResponse response) {
        HttpRequestBody httpRequestBody = request.getHttpRequestBody();
        Map<String, String> parsedRequestBody = httpRequestBody.getFormUrlEncodedRequestBody();
        if (!isValidQueryParams(parsedRequestBody)) {
            ClassLoader classLoader = getClass().getClassLoader();
            response.badRequestResponse(classLoader);
        }
        String account = parsedRequestBody.get(ACCOUNT);
        String password = parsedRequestBody.get(PASSWORD);
        User user = userService.findUser(account, password);

        if (user != null) {
            JSessionId jSessionId = createJSessionId();
            saveSession(jSessionId, user);
            response.redirectResponseByPathAndSetCookie("/index.html", jSessionId.getJSessionIdStr());
            return;
        }
        response.redirectResponseByPath("/401.html");
    }

    private boolean existSession(HttpRequest requestLine) {
        HttpCookie httpCookie = requestLine.getHttpHeader().getHttpCookie();

        if (httpCookie == null) {
            return false;
        }

        if (httpCookie.getJsessionId() == null) {
            return false;
        }

        return sessionHandler.findSession(httpCookie.getJsessionId().getJSessionId().toString()) != null;
    }

    private void saveSession(JSessionId jSessionId, User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        sessionHandler.add(new Session(jSessionId.getJSessionId().toString(), map));
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
