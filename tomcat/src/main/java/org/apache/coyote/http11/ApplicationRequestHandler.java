package org.apache.coyote.http11;

import camp.nextstep.UserController;
import camp.nextstep.service.UnauthroizedUserException;
import org.apache.catalina.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    public static final ApplicationRequestHandler INSTANCE = new ApplicationRequestHandler();
    private static final Logger log = LoggerFactory.getLogger(ApplicationRequestHandler.class);

    private final UserController userController = new UserController();

    @Override
    public HttpResponse service(RequestLine requestLine) {

        if ("/".equals(requestLine.getPath())) {
            final var responseBody = "Hello world!";
            return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.OK, new HttpHeaders(MimeType.TEXT_HTML), new ResponseBody(responseBody.getBytes()));
        }
        if (requestLine.getPath().equals("/login")) {
            try {
                Map<String, Object> queryParamMap = requestLine.getQueryParamMap();
                userController.findUser(queryParamMap);
                Location location = new Location("http://localhost:8080/index.html");
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.FOUND, new HttpHeaders(MimeType.TEXT_HTML, location));
            } catch (UnauthroizedUserException e) {
                Location location = new Location("http://localhost:8080/401.html");
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.FOUND, new HttpHeaders(MimeType.TEXT_HTML, location));
            } catch (RuntimeException e) {
                log.error(e.getMessage());
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, new HttpHeaders(MimeType.TEXT_HTML));
            }
        }

        return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, new HttpHeaders(MimeType.TEXT_HTML));
    }
}
