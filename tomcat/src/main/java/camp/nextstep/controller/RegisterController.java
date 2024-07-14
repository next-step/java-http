package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.AbstractController;
import org.apache.coyote.http11.HttpRequest;
import org.apache.coyote.http11.HttpResponse;

public class RegisterController extends AbstractController {
    private static final String INDEX_PATH = "/index.html";

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws Exception {
        String account = request.getBodyValue("account");
        String password = request.getBodyValue("password");
        String email = request.getBodyValue("email");

        User user = new User(InMemoryUserRepository.getAutoIncrement(), account, password, email);
        InMemoryUserRepository.save(user);
        response.sendRedirect(INDEX_PATH);
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws Exception {
        response.sendResource("/register.html");
    }
}
