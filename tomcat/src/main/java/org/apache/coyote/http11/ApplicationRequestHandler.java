package org.apache.coyote.http11;

import camp.nextstep.UserController;
import org.apache.catalina.ViewModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    public static final ApplicationRequestHandler INSTANCE = new ApplicationRequestHandler();
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
                // TODO: 질문하기
                return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, FileLoader.read("static" + viewModel.path()));
            } catch (IOException | RuntimeException e) {
                return new Response(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, ContentType.TEXT_HTML, StandardCharsets.UTF_8);
            }
        }

        return new Response(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, ContentType.TEXT_HTML, StandardCharsets.UTF_8);
    }
}
