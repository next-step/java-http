package camp.nextstep.config;

import camp.nextstep.controller.DefaultController;
import camp.nextstep.controller.LoginController;
import camp.nextstep.controller.RegisterController;

import java.util.List;

public class ControllerConfig {

    public static void initialize() {
        List.of(new RegisterController(), new LoginController(), new DefaultController());
    }

}
