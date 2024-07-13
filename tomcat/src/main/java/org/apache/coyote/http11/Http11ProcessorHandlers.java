package org.apache.coyote.http11;

import org.apache.coyote.*;
import org.apache.coyote.handler.*;
import org.apache.file.FileListLoader;

import java.nio.file.Path;
import java.util.Map;

public class Http11ProcessorHandlers {
    private static final ResourceHandler resourceHandler = new ResourceHandler();
    private static final DefaultHandler defaultHandler = new DefaultHandler();
    private static final String DEFAULT_RESOURCE_PATH = "static";
    private static final String RESOURCE_MATCHER = "^.*(html|css|svg|js)";
    private final Map<String, Handler> handlers;
    private final Map<String, Path> resources;

    public Http11ProcessorHandlers() {
        handlers = Map.of(
                "/register", new RegisterHandler(),
                "/login", new LoginHandler()
        );
        resources = new FileListLoader(DEFAULT_RESOURCE_PATH).load();
    }

    public HttpResponse handle(HttpRequest request) {
        var handler = handlers.get(request.path());
        if (handler == null && resources.get(request.path()) != null) {
            return toResource(request);
        }

        if (handler == null) {
            handler = defaultHandler;
        }

        return handle(handler, request);
    }

    private HttpResponse handle(Handler handler, HttpRequest request) {
        try {
            if (request.isGet() && !(handler instanceof DefaultHandler)) {
                return toResource(request);
            }
            return handler.handle(request);
        } catch (NotSupportHandlerException ignored) {
            return null;
        }
    }

    private HttpResponse toResource(HttpRequest request) {
        var path = request.path();
        if (!path.matches(RESOURCE_MATCHER)) {
            path = path + ".html";
        }

        var filePath = resources.get(path);
        return resourceHandler.handle(filePath);
    }
}
