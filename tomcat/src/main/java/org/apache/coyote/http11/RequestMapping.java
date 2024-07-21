package org.apache.coyote.http11;

import org.apache.coyote.http11.handler.DefaultHandler;
import org.apache.coyote.http11.handler.NotFoundHandler;
import org.apache.coyote.http11.handler.StaticResourceHandler;

import java.util.HashMap;
import java.util.Map;

public final class RequestMapping {

    private static final Map<String, RequestHandler> handlers = new HashMap<>();

    static {
        handlers.put("/", new DefaultHandler());
        handlers.put("/index.html", new StaticResourceHandler());
    }

    private RequestMapping() {
    }

    public static void addHandlers(Map<String, RequestHandler> handlers) {
        RequestMapping.handlers.putAll(handlers);
    }

    public static RequestHandler findHandler(String requestPath) {
        if(isStaticResource(requestPath)) {
            return new StaticResourceHandler();
        }

        return handlers.getOrDefault(requestPath, new NotFoundHandler());
    }

    private static boolean isStaticResource(String requestPath) {
        return requestPath.startsWith("/css/") ||
                requestPath.startsWith("/assets/") ||
                requestPath.startsWith("/js/");
    }

}
