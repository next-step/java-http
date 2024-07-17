package camp.nextstep.controller;

import org.apache.coyote.request.HttpRequest;

import java.util.Map;

public class RequestMapping {
    private static final String ROOT = "/";
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";

    private static final Map<String, Controller> controllers = Map.of(
            ROOT, new RootController(),
            REGISTER, new RegisterController(),
            LOGIN, new LoginController()
    );
    private static final Controller STATIC_FILE_CONTROLLER = new StaticFileController();

    private RequestMapping() {
        throw new IllegalStateException("인스턴스 생성 불가");
    }

    public static Controller get(HttpRequest request) {
        if (controllers.containsKey(request.getHttpPath())) {
            return controllers.get(request.getHttpPath());
        }
        return STATIC_FILE_CONTROLLER;
    }
}
