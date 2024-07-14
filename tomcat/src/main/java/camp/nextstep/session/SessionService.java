package camp.nextstep.session;

import camp.nextstep.model.User;
import org.apache.catalina.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class SessionService {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);
    private static final String USER_SESSION_KEY = "user";

    public void signInAs(final Session session, final User user) {
        requireNonNull(session).setAttribute(USER_SESSION_KEY, user);

        log.debug("로그인: {}", user);
    }

    public boolean isLoggedIn(final Session session) {
        return requireNonNull(session).getAttribute(USER_SESSION_KEY) != null;
    }
}
