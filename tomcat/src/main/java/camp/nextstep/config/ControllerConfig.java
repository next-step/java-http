package camp.nextstep.config;

import camp.nextstep.controller.DefaultController;
import camp.nextstep.controller.LoginController;
import camp.nextstep.controller.RegisterController;
import org.apache.coyote.RequestMapping;

import java.util.List;

public class ControllerConfig {

    public static void initialize() {
        List.of(new RegisterController(), new LoginController());
        var defaultController = new DefaultController();
        RequestMapping.addDefault(defaultController);
    }

}
