package camp.nextstep.controller;

import camp.nextstep.http.domain.HttpPath;

import java.util.Map;

public class RequestMapping {

    private final Map<HttpPath, Controller> controllers;

    public RequestMapping() {
        controllers = Map.of(
                new HttpPath("/"), new RootController(),
                new HttpPath("/login"), new LoginController(),
                new HttpPath("/register"), new RegisterController()
        );
    }

    public Controller getController(final HttpPath path) {
        return controllers.get(path);
    }


}
