package org.apache.coyote.http11.request;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.constant.ContentType;
import org.apache.coyote.http11.request.handler.DefaultHandler;
import org.apache.coyote.http11.request.handler.NotFoundHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapper {
    private static final Map<String, RequestHandler> handlerMap = new HashMap<>();

    private RequestHandlerMapper() {
        handlerMap.put("/index.html", new DefaultHandler());
        handlerMap.put("/401.html", new DefaultHandler());
    }

    public static RequestHandlerMapper getInstance() {
        return RequestHandlerMapper.SingletonHelper
                .SINGLETON;
    }

    public RequestHandler findHandler(String path) {
        if (ContentType.isStaticType(path)) {
            return new DefaultHandler();
        }

        return handlerMap.getOrDefault(path, new NotFoundHandler());
    }

    public String response(HttpRequest httpRequest) throws IOException {
        return findHandler(httpRequest.httpRequestHeader().requestLine().url())
                .handle(httpRequest);
    }

    public void addHandler(final String path, final RequestHandler handler) {
        handlerMap.put(path, handler);
    }

    private static class SingletonHelper {
        private static final RequestHandlerMapper SINGLETON = new RequestHandlerMapper();
    }
}
