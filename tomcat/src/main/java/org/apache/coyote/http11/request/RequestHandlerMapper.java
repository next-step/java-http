package org.apache.coyote.http11.request;

import org.apache.coyote.http11.model.constant.ContentType;
import org.apache.coyote.http11.request.handler.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapper {
    private final Map<String, RequestHandler> handlerMap;

    private RequestHandlerMapper() {
        Map<String, RequestHandler> tempMap = new HashMap<>();
        tempMap.put("/login.html", new LoginHandler());
        tempMap.put("/index.html", new DefaultHandler());
        tempMap.put("/401.html", new DefaultHandler());
        tempMap.put("/register.html", new RegisterHandler());
        this.handlerMap = Collections.unmodifiableMap(tempMap);
    }

    public static RequestHandlerMapper getInstance() {
        return RequestHandlerMapper.SingletonHelper
                .SINGLETON;
    }

    public RequestHandler getHandler(final String path) {
        if (ContentType.isStaticType(path)) {
            return new DefaultHandler();
        }

        return handlerMap.getOrDefault(path, new NotFoundHandler());
    }

    private static class SingletonHelper {
        private static final RequestHandlerMapper SINGLETON = new RequestHandlerMapper();
    }
}
