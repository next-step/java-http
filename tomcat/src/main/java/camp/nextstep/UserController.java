package camp.nextstep;

import camp.nextstep.model.User;
import camp.nextstep.service.UserService;
import jakarta.HttpServletRequest;
import jakarta.Cookie;
import org.apache.catalina.Session;
import jakarta.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class UserController {

    private static final String USER = "user";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final static UserService userService = new UserService();


    public ViewModel register() {
        return new ViewModel("/register.html");
    }

    public ViewModel register(UserDto userDto) {
        log.info("register info: {}", userDto);
        userService.register(userDto);
        return new ViewModel("/index.html");
    }


    public ViewModel getLogin(HttpServletRequest httpServletRequest) {
        Session session = httpServletRequest.getSession();
        if (session != null && session.getAttribute(USER) != null) {
            return new ViewModel("/index.html");
        }
        return new ViewModel("/login.html");
    }


    public ViewModel login(HttpServletRequest httpServletRequest) {
        Session oldSession = httpServletRequest.getSession();
        if (oldSession != null && oldSession.getAttribute(USER) != null) {
            return new ViewModel("/index.html", null, Cookie.ofJsessionId(oldSession.getId()), oldSession);
        }
        UserDto userDto = UserDto.map(httpServletRequest.getMessageBody().toMap());
        User user = userService.login(userDto);

        Session newSession = httpServletRequest.getSession(true);
        newSession.setAttribute(USER, user);
        Cookie cookie = Cookie.ofJsessionId(newSession.getId());

        return new ViewModel("/index.html", null, cookie, newSession);
    }

}
