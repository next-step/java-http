package org.apache.coyote.http11.response;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.cookie.Cookie;
import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.model.Path;
import org.apache.coyote.http11.request.model.RequestBodies;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ResponseHandler {

    private final String response;

    private ResponseHandler(final String response) {
        this.response = response;
    }

    public static ResponseHandler handler(final HttpRequest httpRequest) throws IOException {
        if (HttpMethod.POST.equals(httpRequest.getHttpMethod())) {
            // todo: response 구현 예정
            return postResponseResource(httpRequest);
        }
        return getResponseResource(httpRequest);
    }

    private static ResponseHandler postResponseResource(Path path, RequestBodies requestBodies, Cookies cookies) throws IOException {
        if (path.urlPath().equals("/register")) {
            String account = requestBodies.getRequestBodyValueByKey("account");
            String password = requestBodies.getRequestBodyValueByKey("password");
            String email = requestBodies.getRequestBodyValueByKey("email");

            InMemoryUserRepository.save(new User(account, password, email));

            String responseBody = new ResponseBody2("/index.html").getResponseBody();
            return new ResponseHandler(responseBody, "/index.html", StatusCode.FOUND, Cookies.emptyCookies());
        }

        if (path.urlPath().equals("/login")) {
            String account = requestBodies.getRequestBodyValueByKey("account");
            String password = requestBodies.getRequestBodyValueByKey("password");
            boolean loginSuccess = login(account, password, cookies);

            if (loginSuccess) {
                String filePath = "/index.html";
                String responseBody = new ResponseBody2(filePath).getResponseBody();
                return new ResponseHandler(responseBody, filePath, StatusCode.FOUND, cookies);
            }

            String filePath = "/401.html";
            String responseBody = new ResponseBody2(filePath).getResponseBody();
            return new ResponseHandler(responseBody, filePath, StatusCode.NOT_FOUND, cookies);
        }
        String responseBody = new ResponseBody2(path.urlPath()).getResponseBody();
        return new ResponseHandler(responseBody, path.urlPath(), StatusCode.OK, cookies);
    }

    private static ResponseHandler getResponseResource(Path path, Cookies cookies) throws IOException {
        if (isRootPath(path)) {
            String filePath = "/index.html";
            String responseBody = new ResponseBody2(filePath).getResponseBody();
            return new ResponseHandler(responseBody, filePath, StatusCode.OK, cookies);
        }

        if (path.urlPath().equals("/login")) {
            if (cookies.hasJSessionId()) {
                String jSessionId = cookies.getJSessionId();
                HttpSession jsessionid = SessionManager.getInstance().findSession(jSessionId);

                if (jsessionid != null) {
                    String filePath = "/index.html";
                    String responseBody = new ResponseBody2(filePath).getResponseBody();
                    return new ResponseHandler(responseBody, filePath, StatusCode.OK, cookies);
                }
            }

            String filePath = "/login.html";
            String responseBody = new ResponseBody2(filePath).getResponseBody();
            return new ResponseHandler(responseBody, filePath, StatusCode.OK, cookies);
        }

        if (path.urlPath().equals("/register")) {
            String filePath = "/register.html";
            String responseBody = new ResponseBody2(filePath).getResponseBody();
            return new ResponseHandler(responseBody, filePath, StatusCode.OK, cookies);
        }

        String responseBody = new ResponseBody2(path.urlPath()).getResponseBody();
        return new ResponseHandler(responseBody, path.urlPath(), StatusCode.OK, cookies);
    }

    public Cookies getCookies() {
        return cookies;
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

    private static boolean login(String account, String password, Cookies cookies) {
        final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NoSuchElementException::new);
        if (cookies.hasJSessionId()) {
            String jSessionId = cookies.getJSessionId();
            return SessionManager.getInstance().isExistJSession(jSessionId);
        }

        if (user.checkPassword(password)) {
            String uuid = UUID.randomUUID().toString();
            cookies.addCookie(new Cookie("JSESSIONID", uuid));
            SessionManager.getInstance().add(new Session(uuid, user));
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
