package camp.nextstep.request;

import camp.nextstep.request.controller.Controller;
import camp.nextstep.request.controller.LoginController;
import camp.nextstep.request.controller.RegisterController;
import org.apache.coyote.http11.request.RequestHandlerMapper;

public class RequestMappingInitializer {

    public static void init() {
        final RequestHandlerMapper requestHandlerMapper = RequestHandlerMapper.getInstance();

        final Controller registerController = RegisterController.getInstance();
        requestHandlerMapper.addHandler(registerController.url(), registerController);

        final Controller loginController = LoginController.getInstance();
        requestHandlerMapper.addHandler(loginController.url(), loginController);
    }
}
