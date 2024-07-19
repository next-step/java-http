package org.apache.coyote.http11;


import org.apache.coyote.http11.request.RequestLine;

import java.util.Map;

public class RequestMapping {

    private static final Map<String, RequestHandler> REQUEST_HANDLER_MAP = Map.of(
            "/", ApplicationRequestHandler.INSTANCE,
            "/login", ApplicationRequestHandler.INSTANCE,
            "/register", ApplicationRequestHandler.INSTANCE
    );

    public RequestHandler getHandler(RequestLine requestLine) {
        if (FileLoader.isStaticResource(requestLine.getPath())) {
            return StaticResourceRequestHandler.INSTANCE;
        }
        return REQUEST_HANDLER_MAP.getOrDefault(requestLine.getPath(), NotFoundHandler.INSTANCE);
    }
}
