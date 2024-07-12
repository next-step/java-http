package camp.nextstep.request;

import camp.nextstep.request.controller.Controller;
import camp.nextstep.request.controller.LoginController;
import camp.nextstep.request.handler.RegisterHandler;
import org.apache.coyote.http11.request.RequestHandlerMapper;

public class RequestMappingInitializer {

    public static void init() {
        final RequestHandlerMapper requestHandlerMapper = RequestHandlerMapper.getInstance();

        requestHandlerMapper.addHandler("/register.html", new RegisterHandler());

        final Controller loginController = LoginController.getInstance();
        requestHandlerMapper.addHandler(loginController.url(), loginController);
    }
}
