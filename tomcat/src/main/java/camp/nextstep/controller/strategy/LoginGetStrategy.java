package camp.nextstep.controller.strategy;

import camp.nextstep.controller.session.Session;
import camp.nextstep.controller.session.SessionManager;
import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.util.Map;
import java.util.UUID;
import org.apache.coyote.http11.HttpEntity;
import org.apache.coyote.http11.exception.UnAuthorizedException;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.HttpResponse;

public class LoginGetStrategy implements RequestMethodStrategy {

    public static final String INDEX_HTML = "/index.html";
    public static final String GET = "GET";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
                .equals(RequestMethod.GET.name())
                && !httpRequest.getParams().isEmpty();
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        Map<String, String> userParams = httpRequest.getParams();

        User user = login(userParams);

        Map<String, String> session = httpRequest
                .getSession()
                .orElseGet(() -> Map.of("JSESSIONID", startSession(user).getId()));
        return HttpEntity.redirect(INDEX_HTML);
    }

    private User login(Map<String, String> userParams) {
        User user = InMemoryUserRepository.findByAccount(userParams.get(ACCOUNT))
                .orElseThrow(() -> new UnAuthorizedException("존재하지 않는 사용자 입니다."));

        user.checkPassword(userParams.get(PASSWORD));

        return user;
    }

    private Session startSession(User user) {
        final Session session = new Session(UUID.randomUUID(), user);
        SessionManager.add(session);
        return session;
    }
}
