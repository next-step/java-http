package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import camp.nextstep.request.HttpQueryParameters;
import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;
import org.apache.coyote.http11.Http11Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class RegisterController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.renderStaticResource("/register.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        final HttpQueryParameters requestBody = request.getRequestBody();

        final String account = requireNonNull(requestBody.getString("account"));
        final String email = requireNonNull(requestBody.getString("email"));
        final String password = requireNonNull(requestBody.getString("password"));

        User user = new User(account, password, email);
        InMemoryUserRepository.save(user);
        request.signInAs(user, getSessionManager());
        log.debug("사용자 생성 후 로그인: {}", user);

        response.redirectTo("/index.html");
    }
}
