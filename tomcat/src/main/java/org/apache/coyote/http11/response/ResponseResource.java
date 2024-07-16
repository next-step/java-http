package org.apache.coyote.http11.response;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.request.model.*;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ResponseResource {

    private final String responseBody;
    private final String filePath;
    private final StatusCode statusCode;
    private final Cookies cookies;

    private ResponseResource(final String responseBody, String filePath, StatusCode statusCode, Cookies cookies) {
        this.responseBody = responseBody;
        this.filePath = filePath;
        this.statusCode = statusCode;
        this.cookies = cookies;
    }

    public static ResponseResource of(final Path path, final RequestBodies requestBodies, final HttpMethod httpMethod, final Cookies cookies) throws IOException {
        if (HttpMethod.POST.name().equals(httpMethod.name())) {
            return postResponseResource(path, requestBodies, cookies);
        }
        return getResponseResource(path, cookies);
    }

    private static ResponseResource postResponseResource(Path path, RequestBodies requestBodies, Cookies cookies) throws IOException {
        if (path.urlPath().equals("/register")) {
            String account = requestBodies.getRequestBodyValueByKey("account");
            String password = requestBodies.getRequestBodyValueByKey("password");
            String email = requestBodies.getRequestBodyValueByKey("email");

            InMemoryUserRepository.save(new User(account, password, email));

            String responseBody = new ResponseBody("/index.html").getResponseBody();
            return new ResponseResource(responseBody, "/index.html", StatusCode.FOUND, Cookies.emptyCookies());
        }

        if (path.urlPath().equals("/login")) {
            String account = requestBodies.getRequestBodyValueByKey("account");
            String password = requestBodies.getRequestBodyValueByKey("password");
            boolean loginSuccess = login(account, password, cookies);

            if (loginSuccess) {
                String filePath = "/index.html";
                String responseBody = new ResponseBody(filePath).getResponseBody();
                return new ResponseResource(responseBody, filePath, StatusCode.FOUND, cookies);
            }

            String filePath = "/401.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.NOT_FOUND, Cookies.emptyCookies());
        }
        String responseBody = new ResponseBody(path.urlPath()).getResponseBody();
        return new ResponseResource(responseBody, path.urlPath(), StatusCode.OK, Cookies.emptyCookies());
    }

    private static ResponseResource getResponseResource(Path path, Cookies cookies) throws IOException {
        if (isRootPath(path)) {
            String filePath = "/index.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.OK, Cookies.emptyCookies());
        }

        if (path.urlPath().equals("/login")) {
            if (cookies.hasJSessionId()) {
                String jSessionId = cookies.getJSessionId();
                HttpSession jsessionid = SessionManager.getInstance().findSession(jSessionId);

                if (jsessionid != null) {
                    String filePath = "/index.html";
                    String responseBody = new ResponseBody(filePath).getResponseBody();
                    return new ResponseResource(responseBody, filePath, StatusCode.OK, cookies);
                }
            }

            String filePath = "/login.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.OK, Cookies.emptyCookies());
        }

        if (path.urlPath().equals("/register")) {
            String filePath = "/register.html";
            String responseBody = new ResponseBody(filePath).getResponseBody();
            return new ResponseResource(responseBody, filePath, StatusCode.OK, Cookies.emptyCookies());
        }

        String responseBody = new ResponseBody(path.urlPath()).getResponseBody();
        return new ResponseResource(responseBody, path.urlPath(), StatusCode.OK, Cookies.emptyCookies());
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
