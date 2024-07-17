package camp.nextstep;

import camp.nextstep.model.User;
import camp.nextstep.service.UserService;
import jakarta.HttpServletRequest;
import org.apache.catalina.Cookie;
import org.apache.catalina.Session;
import org.apache.catalina.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final static UserService userService = new UserService();


    public ViewModel register() {
        return new ViewModel("/register.html");
    }

    public ViewModel register(Map<String, Object> queryParamMap) {
        log.info("register info: {}", queryParamMap);
        User user = userService.register(queryParamMap);

        return new ViewModel("/index.html");
    }


    public ViewModel getLogin(HttpServletRequest httpServletRequest) {
        Session session = httpServletRequest.getSession();
        if (session != null && session.getAttribute("user") != null) {
            return new ViewModel("/index.html");
        }
        return new ViewModel("/login.html");
    }


    public ViewModel login(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession() != null) {
            Session session = httpServletRequest.getSession();
            return new ViewModel("/index.html", null, Cookie.ofJsessionId(session.getId()), session);
        }
        Map<String, Object> requestBody = httpServletRequest.getRequestBody();
        User user = userService.login(requestBody);

        Session session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
        Cookie cookie = Cookie.ofJsessionId(session.getId());

        return new ViewModel("/index.html", null, cookie, session);
    }

}
