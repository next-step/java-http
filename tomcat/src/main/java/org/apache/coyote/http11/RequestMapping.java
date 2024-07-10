package org.apache.coyote.http11;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.http11.handler.LoginRequestHandler;
import org.apache.coyote.http11.handler.RegisterRequestHandler;
import org.apache.coyote.http11.handler.RequestHandler;
import org.apache.coyote.http11.handler.RootRequestHandler;
import org.apache.coyote.http11.request.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.utils.FileUtils;

public class RequestMapping {

    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";
    private static final String ROOT_PATH = "/";
    private static final String NOT_FOUND_PATH = "/404.html";

    private final Map<String, RequestHandler> handlers = new HashMap<>();

    public RequestMapping() {
        handlers.put(ROOT_PATH, new RootRequestHandler());
        handlers.put(LOGIN_PATH, new LoginRequestHandler());
        handlers.put(REGISTER_PATH, new RegisterRequestHandler());
    }

    public Response handleMapping(Request request) throws IOException {
        RequestLine requestLine = request.getRequestLine();
        HttpPath path = requestLine.getPath();
        RequestHandler handler = handlers.get(path.getPath());

        if (handler != null) {
            return handler.handle(request);
        }

        String responseBody = FileUtils.getStaticFileContent(path);
        if (responseBody.isEmpty()) {
            return Response.notFound(FileUtils.getStaticFileContent(HttpPath.from(NOT_FOUND_PATH)));
        }
        return Response.ok(ContentType.from(path.getExtension()), responseBody);
    }
}
