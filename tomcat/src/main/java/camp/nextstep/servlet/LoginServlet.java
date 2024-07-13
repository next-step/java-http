package camp.nextstep.servlet;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import com.javax.servlet.http.HttpServlet;
import org.apache.coyote.http.Cookie;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.http.StatusCode;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        StaticResource staticResource = StaticResourceResolver.findStaticResource("/login.html");
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final String account = httpRequest.getParameter("account");
        final String password = httpRequest.getParameter("password");

        if (account == null) {
            setResponse("/401.html", httpResponse, StatusCode.UNAUTHORIZED);
            return;
        }

        final User foundAccount = InMemoryUserRepository.findByAccount(account).orElse(null);

        if (isInvalidLoginUser(foundAccount, password)) {
            setResponse("/401.html", httpResponse, StatusCode.UNAUTHORIZED);
            return;
        }

        log.debug("user: {}", foundAccount);

        setResponse("/index.html", httpResponse, StatusCode.FOUND);
    }

    private void setResponse(final String filePath, final HttpResponse httpResponse, final StatusCode unauthorized) throws IOException {
        final StaticResource staticResource = StaticResourceResolver.findStaticResource(filePath);
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
        httpResponse.setStatusCode(unauthorized);
    }

    private boolean isInvalidLoginUser(final User foundAccount, final String password) {
        return foundAccount == null || !foundAccount.checkPassword(password);
    }
}
