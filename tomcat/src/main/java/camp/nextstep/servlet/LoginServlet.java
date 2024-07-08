package camp.nextstep.servlet;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import com.javax.servlet.Servlet;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginServlet implements Servlet {

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void service(final Request request, final Response response) throws Exception {
        response.setStaticResource("login.html");
        final String account = request.getParameter("account");
        final User byAccount = InMemoryUserRepository.findByAccount(account).orElse(null);

        log.debug("user: {}", byAccount);
    }
}
