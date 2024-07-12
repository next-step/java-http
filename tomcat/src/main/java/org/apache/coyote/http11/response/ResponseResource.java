package org.apache.coyote.http11.response;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.request.model.HttpMethod;
import org.apache.coyote.http11.request.model.Path;
import org.apache.coyote.http11.request.model.QueryStrings;
import org.apache.coyote.http11.request.model.RequestBodies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ResponseResource {

    private static final Logger log = LoggerFactory.getLogger(ResponseResource.class);

    private final String responseBody;
    private final String filePath;
    private final StatusCode statusCode;

    private ResponseResource(final String responseBody, String filePath, StatusCode statusCode) {
        this.responseBody = responseBody;
        this.filePath = filePath;
        this.statusCode = statusCode;
    }

    public static ResponseResource of(final Path path, final RequestBodies requestBodies, final HttpMethod httpMethod) throws IOException {
        if (HttpMethod.POST.name().equals(httpMethod.name())) {
            return postResponseResource(path, requestBodies);
        }
        return getResponseResource(path);
    }

    private static ResponseResource postResponseResource(Path path, RequestBodies requestBodies) throws IOException {
        if (path.urlPath().equals("/register")) {
            String account = requestBodies.getRequestBodyValueByKey("account");
            String password = requestBodies.getRequestBodyValueByKey("password");
            String email = requestBodies.getRequestBodyValueByKey("email");
            InMemoryUserRepository.save(new User(account, password, email));
            String responseBody = new ResponseBody("/index.html").getResponseBody();
            return new ResponseResource(responseBody, "/index.html", StatusCode.FOUND);
        }
        if (path.urlPath().equals("/login")) {
            String account = requestBodies.getRequestBodyValueByKey("account");
            String password = requestBodies.getRequestBodyValueByKey("password");
            boolean loginSuccess = login(account, password);
            if (loginSuccess) {
                String filePath = "/index.html";
                String responseBody = new ResponseBody(filePath).getResponseBody();
                return new ResponseResource(responseBody, filePath, StatusCode.FOUND);
            }
            String filePath = "/401.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.NOT_FOUND);
        }
        String responseBody = new ResponseBody(path.urlPath()).getResponseBody();
        return new ResponseResource(responseBody, path.urlPath(), StatusCode.OK);
    }

    private static ResponseResource getResponseResource(Path path) throws IOException {
        if (isRootPath(path)) {
            String filePath = "/index.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.OK);
        }

        if (path.urlPath().equals("/login")) {
            String filePath = "/login.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.OK);
        }

        if (path.urlPath().equals("/register")) {
            String filePath = "/register.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.OK);
        }

        String responseBody = new ResponseBody(path.urlPath()).getResponseBody();
        return new ResponseResource(responseBody, path.urlPath(), StatusCode.OK);
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getFilePath() {
        return filePath;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String parseExtension() {
        if (hasNotExtension()) {
            throw new NoSuchElementException("확장자가 없습니다.");
        }
        return filePath.substring(filePath.lastIndexOf("."));
    }

    private static boolean login(String account, String password) {
        final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NoSuchElementException::new);
        if (user.checkPassword(password)) {
            log.info("user {}", user);
            return true;
        }
        return false;
    }

    private static boolean isRootPath(final Path path) {
        return path.urlPath().equals("/");
    }

    private boolean hasNotExtension() {
        return filePath.lastIndexOf(".") < 0;
    }
}
