package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static camp.nextstep.db.InMemoryUserRepository.findByAccount;
import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class LoginHttpRequestHandler implements HttpRequestHandler {
    private final String LOGIN_PATH = "/login";
    private final String LOGIN_PAGE_PATH = "/login.html";
    private final String ACCOUNT = "account";

    @Override
    public boolean isMatchPathPattern(String path) {
        return path.startsWith(LOGIN_PATH);
    }
    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        if (!isLoginPageRequest(requestLine.getPath().getUrlPath())) {
            checkUser(requestLine.getPath().getQueryParams());
        }

        StaticResource staticResource = createResourceFromPath(
                LOGIN_PAGE_PATH,
                getClass().getClassLoader()
        );

        try {
            return HttpResponse.createResponseByFile(staticResource.getResourceFile());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                return HttpResponse.createResponseByFile(new File("static/404.html"));
            } catch (IOException ex) {
                return HttpResponse.createNotFoundResponseByString();
            }
        }
    }

    private boolean isLoginPageRequest(String path) {
        return path.contains(LOGIN_PAGE_PATH);
    }

    private void checkUser(Map<String, String> queryParams) {
        if (!queryParams.containsKey(ACCOUNT)) {
            return;
        }
        String account = queryParams.get(ACCOUNT);

        findByAccount(account).ifPresent(user -> System.out.println(user));
    }
}
