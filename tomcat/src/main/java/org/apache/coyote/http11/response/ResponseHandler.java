package org.apache.coyote.http11.response;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.cookie.Cookie;
import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ResponseHandler {

    private final Response response;

    private ResponseHandler(final Response response) {
        this.response = response;
    }

    public static ResponseHandler handler(final HttpRequest httpRequest) throws IOException {
        if (HttpMethod.POST.equals(httpRequest.getHttpMethod())) {
            return post(httpRequest);
        }
        return get(httpRequest);
    }

    private static ResponseHandler post(HttpRequest httpRequest) throws IOException {
        if (httpRequest.getUrlPath().equals("/register")) {
            String account = httpRequest.getRequestBodyValueByKey("account");
            String password = httpRequest.getRequestBodyValueByKey("password");
            String email = httpRequest.getRequestBodyValueByKey("email");

            InMemoryUserRepository.save(new User(account, password, email));

            HttpResponse httpResponse = HttpResponse.responseOk(httpRequest);
            Response response = Response.parsingResponse(httpResponse);
            return new ResponseHandler(response);
        }

        if (httpRequest.getUrlPath().equals("/login")) {
            String account = httpRequest.getRequestBodyValueByKey("account");
            String password = httpRequest.getRequestBodyValueByKey("password");
            boolean loginSuccess = login(account, password, httpRequest.getCookies());

            if (loginSuccess) {
                HttpResponse httpResponse = HttpResponse.redirectRoot(httpRequest);
                Response response = Response.parsingResponse(httpResponse);
                return new ResponseHandler(response);
            }

            HttpResponse httpResponse = HttpResponse.responseUnAuthorized(httpRequest);
            Response response = Response.parsingResponse(httpResponse);
            return new ResponseHandler(response);
        }

        HttpResponse httpResponse = HttpResponse.responseOk(httpRequest);
        Response response = Response.parsingResponse(httpResponse);
        return new ResponseHandler(response);
    }

    private static ResponseHandler get(HttpRequest httpRequest) throws IOException {
        if (isRootPath(httpRequest)) {
            HttpResponse httpResponse = HttpResponse.redirect(httpRequest,"/index.html");
            Response response = Response.parsingResponse(httpResponse);
            return new ResponseHandler(response);
        }

        if (httpRequest.getUrlPath().equals("/login")) {
            if (httpRequest.hasJSessionId()) {
                String jSessionId = httpRequest.getJSessionId();
                HttpSession jsessionid = SessionManager.getInstance().findSession(jSessionId);

                if (jsessionid != null) {
                    HttpResponse httpResponse = HttpResponse.redirectRoot(httpRequest);
                    Response response = Response.parsingResponse(httpResponse);
                    return new ResponseHandler(response);
                }
            }

            HttpResponse httpResponse = HttpResponse.responseOkWithOutHtml(httpRequest);
            Response response = Response.parsingResponse(httpResponse);
            return new ResponseHandler(response);
        }

        if (httpRequest.getUrlPath().equals("/register")) {
            HttpResponse httpResponse = HttpResponse.responseOkWithOutHtml(httpRequest);
            Response response = Response.parsingResponse(httpResponse);
            return new ResponseHandler(response);
        }

        HttpResponse httpResponse = HttpResponse.responseOk(httpRequest);
        Response response = Response.parsingResponse(httpResponse);
        return new ResponseHandler(response);
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

    private static boolean isRootPath(HttpRequest httpRequest) {
        return httpRequest.getUrlPath().equals("/");
    }

    public String getResponse() {
        return response.getResponse();
    }
}
