package org.apache.coyote;

import org.apache.file.ResourceHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> CONTROLLERS = new HashMap<>();
    private static Controller DEFAULT_CONTROLLER;

    public static void addMapping(final String mappingPath, final Controller controller) {
        CONTROLLERS.put(mappingPath, controller);
    }

    public static void addDefault(final Controller controller) {
        DEFAULT_CONTROLLER = controller;
    }

    public HttpResponse handle(HttpRequest request) {
        var controller = CONTROLLERS.get(request.path());
        if (controller != null) {
            return handle(controller, request);
        }

        var file = handleResource(request);
        if (file != null) {
            return file;
        }

        return handle(DEFAULT_CONTROLLER, request);
    }

    private HttpResponse handle(Controller controller, HttpRequest request) {
        try {
            return controller.service(request);
        } catch (Exception ignored) {
            return null;
        }
    }

    private HttpResponse handleResource(HttpRequest request) {
        try {
            return ResourceHandler.handle(request.path());
        } catch (IOException ignored) {
            return null;
        }
    }
}
