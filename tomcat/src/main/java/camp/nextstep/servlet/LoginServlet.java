package camp.nextstep.servlet;

import camp.nextstep.model.User;
import camp.nextstep.service.UserService;
import com.javax.servlet.http.HttpServlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet extends HttpServlet {

    private final UserService userService;

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

    public LoginServlet(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        if (userService.isNotLogin(httpRequest.getSession(true))) {
            httpResponse.sendRedirect("/index.html");

            return;
        }

        StaticResource staticResource = StaticResourceResolver.findStaticResource("/login.html");
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final String account = httpRequest.getParameter("account");
        final String password = httpRequest.getParameter("password");

        if (account == null) {
            httpResponse.sendRedirect("/401.html");
            return;
        }

        final User user = userService.findUserByAccount(account);

        if (userService.isInvalidLoginUser(user, password)) {
            httpResponse.sendRedirect("/401.html");
            return;
        }

        log.debug("user: {}", user);

        userService.login(user, httpRequest.getSession(true));

        httpResponse.sendRedirect("/index.html");
    }
}
