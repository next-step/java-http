package camp.nextstep.servlet;

import camp.nextstep.service.UserService;
import com.javax.servlet.http.HttpServlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.http.StatusCode;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;

public class RegisterServlet extends HttpServlet {

    private final UserService userService;

    public RegisterServlet(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        StaticResource staticResource = StaticResourceResolver.findStaticResource("/register.html");
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final String account = httpRequest.getParameter("account");
        final String password = httpRequest.getParameter("password");
        final String email = httpRequest.getParameter("email");

        if (userService.isInvalidUserInfo(account, password, email)) {
            StaticResource staticResource = StaticResourceResolver.findStaticResource("/register.html");
            httpResponse.setStatusCode(StatusCode.BAD_REQUEST);
            httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());

            return;
        }

        userService.register(account, password, email);

        httpResponse.sendRedirect("/index.html");
    }
}
