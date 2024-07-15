package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.AbstractController;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.file.ResourceHandler;
import org.apache.http.HttpPath;

import java.io.IOException;

public class RegisterController extends AbstractController {
    private static final String MAPPING_PATH = "/register";
    private static final String SUCCESS_PAGE = "/index.html";

    public RegisterController() {
        super(MAPPING_PATH);
    }

    @Override
    public HttpResponse doPost(final HttpRequest request) {
        var registerRequest = new RegisterRequest(request);
        register(registerRequest);
        return new HttpResponse(new HttpPath(SUCCESS_PAGE));
    }

    @Override
    public HttpResponse doGet(final HttpRequest request) throws IOException {
        var registerPage = request.path() + ".html";
        return ResourceHandler.handle(registerPage);
    }

    private void register(final RegisterRequest request) {
        var user = new User(request.account(), request.password(), request.email());
        InMemoryUserRepository.save(user);
    }

}
