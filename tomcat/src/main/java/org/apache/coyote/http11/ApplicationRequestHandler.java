package org.apache.coyote.http11;

import camp.nextstep.UserController;
import org.apache.catalina.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    public static final ApplicationRequestHandler INSTANCE = new ApplicationRequestHandler();
    private static final Logger log = LoggerFactory.getLogger(ApplicationRequestHandler.class);

    private final UserController userController = new UserController();

    @Override
    public HttpResponse service(RequestLine requestLine) {

        if ("/".equals(requestLine.getPath())) {
            final var responseBody = "Hello world!";
            return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, new ResponseBody(responseBody.getBytes()));
        }
        if (requestLine.getPath().equals("/login")) {
            try {
                Map<String, Object> queryParamMap = requestLine.getQueryParamMap();
                ViewModel viewModel = userController.findUser(queryParamMap);
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, new ResponseBody(FileLoader.read("static" + viewModel.path())));
            } catch (IOException | RuntimeException e) {
                log.error(e.getMessage());
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, ContentType.TEXT_HTML);
            }
        }

        return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, ContentType.TEXT_HTML);
    }
}
