package camp.nextstep.servlet;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import com.javax.servlet.Servlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet implements Servlet {

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final StaticResource staticResource = StaticResourceResolver.findStaticResource("login.html");
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());

        final String account = httpRequest.getParameter("account");

        if (account == null) {
            return;
        }

        final User byAccount = InMemoryUserRepository.findByAccount(account).orElse(null);

        log.debug("user: {}", byAccount);
    }
}
