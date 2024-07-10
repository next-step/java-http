package camp.nextstep.controller;

import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;

import java.util.Map;
import java.util.regex.Pattern;

public class RequestMapping {

    private static final Pattern ROOT_PATH = Pattern.compile("/");
    private static final Pattern LOGIN_PATH = Pattern.compile("/login");
    private static final Pattern REGISTER_PATH = Pattern.compile("/register");

    private static final Controller staticController = new StaticController();

    private final Map<Pattern, Controller> controllers;

    public RequestMapping(Map<Pattern, Controller> controllers) {
        this.controllers = controllers;
    }

    public static RequestMapping create() {
        return new RequestMapping(Map.of(
                ROOT_PATH, new RootController(),
                LOGIN_PATH, new LoginController(),
                REGISTER_PATH, new RegisterController()
        ));
    }

    public HttpResponse service(HttpRequest httpRequest) {
        return getController(httpRequest).service(httpRequest);
    }

    private Controller getController(HttpRequest httpRequest) {
        return controllers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().matcher(httpRequest.getHttpPath()).matches())
                .findAny()
                .map(Map.Entry::getValue)
                .orElse(staticController);
    }
}
