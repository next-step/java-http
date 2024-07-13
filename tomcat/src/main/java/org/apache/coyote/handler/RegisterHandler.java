package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.http.HttpPath;

public class RegisterHandler implements Handler {
    private static final String SUCCESS_PAGE = "/index.html";

    @Override
    public HttpResponse handle(HttpRequest request) {
        var registerRequest = new RegisterRequest(request);
        register(registerRequest);
        return new HttpResponse(new HttpPath(SUCCESS_PAGE));
    }

    private void register(final RegisterRequest request) {
        var user = new User(request.account(), request.password(), request.email());
        InMemoryUserRepository.save(user);
    }

}
