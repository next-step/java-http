package camp.nextstep.servlet;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import com.javax.servlet.http.HttpServlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.http.Session;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final Session session = httpRequest.getSession(true);
        final Object user = session.getAttribute("user");

        if (user != null) {
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

        final User user = InMemoryUserRepository.findByAccount(account).orElse(null);

        if (isInvalidLoginUser(user, password)) {
            httpResponse.sendRedirect("/401.html");
            return;
        }

        log.debug("user: {}", user);

        final Session session = httpRequest.getSession(true);
        session.setAttribute("user", user);

        httpResponse.sendRedirect("/index.html");
    }
    private boolean isInvalidLoginUser(final User foundAccount, final String password) {
        return foundAccount == null || !foundAccount.checkPassword(password);
    }
}
