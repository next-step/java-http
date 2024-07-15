package org.apache.coyote.http11;

import camp.nextstep.UserController;
import camp.nextstep.exception.UncheckedServletException;
import org.apache.catalina.ViewModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    private final UserController userController = new UserController();

    @Override
    public Response service(RequestLine requestLine) {

        if ("/".equals(requestLine.getPath())) {
            final var responseBody = "Hello world!";
            return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, responseBody.getBytes());
        }
        if (requestLine.getPath().equals("/login")) {
            Map<String, Object> queryParamMap = requestLine.getQueryParamMap();
            ViewModel viewModel = userController.findUser(queryParamMap);
            try {
                return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, FileLoader.read("static" + viewModel.path()));
            } catch (IOException e) {
                throw new UncheckedServletException(e);
            }
        }

        return new Response(requestLine.getHttpProtocol(), HttpStatusCode.NOT_FOUND, ContentType.TEXT_HTML, StandardCharsets.UTF_8);
    }
}
