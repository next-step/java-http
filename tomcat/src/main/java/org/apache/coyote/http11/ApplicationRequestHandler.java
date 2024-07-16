package org.apache.coyote.http11;

import camp.nextstep.UserController;
import camp.nextstep.service.UnauthroizedUserException;
import org.apache.catalina.ViewModel;
import org.apache.coyote.http11.request.HttpMethod;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    public static final ApplicationRequestHandler INSTANCE = new ApplicationRequestHandler();
    private static final Logger log = LoggerFactory.getLogger(ApplicationRequestHandler.class);

    private final UserController userController = new UserController();

    @Override
    public HttpResponse service(HttpRequest httpRequest) throws IOException {
        final var requestLine = httpRequest.getRequestLine();
        final var requestHeaders = httpRequest.getRequestHeaders();

        if ("/".equals(requestLine.getPath())) {
            final var responseBody = "Hello world!";
            return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.OK, new HttpResponseHeaders(MimeType.TEXT_HTML), new ResponseBody(responseBody.getBytes()));
        }

        if (requestLine.getPath().equals("/login")) {
            try {
                Map<String, Object> queryParamMap = requestLine.getQueryParamMap();
                userController.findUser(queryParamMap);
                var location = Location.of(requestLine.protocol(), requestHeaders.host(), "/index.html");
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML, location));
            } catch (UnauthroizedUserException e) {
                var location = Location.of(requestLine.protocol(), requestHeaders.host(), "/401.html");
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML, location));
            } catch (RuntimeException e) {
                log.error(e.getMessage());
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, new HttpResponseHeaders(MimeType.TEXT_HTML));
            }
        }

        if (HttpMethod.GET == requestLine.getMethod() && requestLine.getPath().equals("/register")) {
            final var viewModel = userController.register();
            return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.OK, new HttpResponseHeaders(MimeType.TEXT_HTML), new ResponseBody(FileLoader.read( "static/" + viewModel.path())));
        }

        if (HttpMethod.POST == requestLine.getMethod() && requestLine.getPath().equals("/register")) {
            ViewModel viewModel = userController.register(httpRequest.getRequestBody().toMap());
            var location = Location.of(requestLine.protocol(), requestHeaders.host(), viewModel.path());
            return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML, location));
        }

        return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, new HttpResponseHeaders(MimeType.TEXT_HTML));
    }
}
