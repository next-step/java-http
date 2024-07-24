package camp.nextstep.http;

import camp.nextstep.controller.Controller;
import camp.nextstep.controller.LoginController;
import camp.nextstep.controller.RegisterController;
import camp.nextstep.controller.RootController;

import java.util.Map;

public class RequestMapping {

    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";

    private final Map<String, Controller> controllerMap;

    public RequestMapping(Map<String,Controller> controllerMap){
        this.controllerMap = controllerMap;
    }

    public boolean isRegisteredPath(final String path){
        return controllerMap.containsKey(path);
    }

    public Controller getController(final String path){
        return controllerMap.get(path);
    }

    public static RequestMapping create() {
        return new RequestMapping(Map.of(
                ROOT_PATH, new RootController(),
                LOGIN_PATH, new LoginController(),
                REGISTER_PATH, new RegisterController()
        ));
    }

}
