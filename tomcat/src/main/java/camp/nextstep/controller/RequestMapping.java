package camp.nextstep.controller;

import camp.nextstep.http.domain.HttpPath;

import java.util.Map;

public class RequestMapping {

    private final Map<HttpPath, Controller> controllers;

    private RequestMapping() {
        controllers = Map.of(
                new HttpPath("/"), new RootController(),
                new HttpPath("/login"), new LoginController(),
                new HttpPath("/register"), new RegisterController()
        );
    }

    public static RequestMapping getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Controller getController(final HttpPath path) {
        return controllers.get(path);
    }

    private static class InstanceHolder {
        private static final RequestMapping INSTANCE = new RequestMapping();
    }
}
