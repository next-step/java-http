package org.apache.coyote.http11;

import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapping {
    private final Map<String, Controller> controllerMap = new HashMap<>();

    public void register(final String path, final Controller controller) {
        controllerMap.put(path, controller);
    }

    public boolean isNotRegisteredPath(final String path) {
        return !controllerMap.containsKey(path);
    }

    public Controller getController(final String path) {
        return controllerMap.get(path);
    }
}
