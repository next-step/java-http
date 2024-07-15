package org.apache.coyote.http11;

import camp.nextstep.controller.Controller;
import camp.nextstep.controller.LoginController;
import camp.nextstep.controller.RegisterController;
import camp.nextstep.controller.RootController;
import camp.nextstep.controller.StaticController;
import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.http11.meta.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestLine;

public class RequestMapping {

    private static RequestMapping INSTANCE;

    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";
    private static final String STATIC_PATH = "static";
    private static final String ROOT_PATH = "/";

    private final Map<String, Controller> handlers = new HashMap<>();

    private RequestMapping() {
        // controller를 자동으로 수집하는 방법을 구현하지 않으므로 각 controller를 직접 등록;
        handlers.put(ROOT_PATH, new RootController());
        handlers.put(LOGIN_PATH, new LoginController());
        handlers.put(REGISTER_PATH, new RegisterController());
        handlers.put(STATIC_PATH, new StaticController());
    }

    public static RequestMapping getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequestMapping();
        }
        return INSTANCE;
    }

    public Controller getController(Request request) {
        RequestLine requestLine = request.getRequestLine();
        HttpPath path = requestLine.getPath();
        return handlers.getOrDefault(path.getPath(), handlers.get(STATIC_PATH));
    }
}
