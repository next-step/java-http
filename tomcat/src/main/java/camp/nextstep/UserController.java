package camp.nextstep;

import camp.nextstep.model.User;
import camp.nextstep.service.UserService;
import org.apache.catalina.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final static UserService userService = new UserService();

    public ViewModel findUser(Map<String, Object> queryParamMap) {
        User user = userService.getUser(queryParamMap);
        log.info("user account: {}", user.getAccount());
        return new ViewModel("/login.html");
    }

    public ViewModel register() {
        return new ViewModel("/register.html");
    }

    public ViewModel register(Map<String, Object> queryParamMap) {
        log.info("register info: {}", queryParamMap);
        User user = userService.register(queryParamMap);

        return new ViewModel("/index.html", true);
    }


    public ViewModel login() {
        return new ViewModel("/login.html");
    }

    public ViewModel login(Map<String, Object> queryParamMap) {
       userService.login(queryParamMap);
        return new ViewModel("/index.html", true);
    }
}
