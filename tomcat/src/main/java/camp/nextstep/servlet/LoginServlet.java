package camp.nextstep.servlet;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import com.javax.servlet.Servlet;
import org.apache.coyote.http.Request;
import org.apache.coyote.http.Response;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet implements Servlet {

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void service(final Request request, final Response response) throws Exception {
        final StaticResource staticResource = StaticResourceResolver.findStaticResource("login.html");
        response.setBody(staticResource.getContent(), staticResource.getMimeType());

        final String account = request.getParameter("account");

        if (account == null) {
            return;
        }

        final User byAccount = InMemoryUserRepository.findByAccount(account).orElse(null);

        log.debug("user: {}", byAccount);
    }
}
