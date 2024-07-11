package camp.nextstep.http.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Resource;
import camp.nextstep.http.domain.Response;
import camp.nextstep.model.User;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static camp.nextstep.db.InMemoryUserRepository.findByAccount;
import static camp.nextstep.http.domain.Resource.createResourceFromPath;

public class LoginRequestHandler implements RequestHandler{
    private final String LOGIN_PATH = "/login";
    private final String LOGIN_PAGE_PATH = "/login.html";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";
    private final String PRINT_FORMAT = "account = %s, email = %s, password = %s";

    @Override
    public boolean isMatchPathPattern(String path) {
        return path.startsWith(LOGIN_PATH);
    }
    @Override
    public Response makeResponse(RequestLine requestLine) {
        if (!isLoginPageRequest(requestLine.getPath().getUrlPath())) {
            checkUser(requestLine.getPath().getQueryParams());
        }

        Resource resource = createResourceFromPath(
                LOGIN_PAGE_PATH,
                getClass().getClassLoader()
        );

        try {
            return Response.createResponseByFile(resource.getResourceFile());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                return Response.createResponseByFile(new File("static/404.html"));
            } catch (IOException ex) {
                return Response.createNotFoundResponseByString();
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
