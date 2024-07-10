package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.file.FileReader;
import org.apache.http.HttpPath;
import org.apache.http.HttpStatus;
import org.apache.http.body.HttpFileBody;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.header.Location;

import java.io.IOException;

public class RegisterHandler implements Handler {
    private static final String MAPPING_PATH = "/register";
    private static final String REGISTER_PAGE = "/register.html";
    private static final String SUCCESS_PAGE = "/index.html";

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (!request.matchPath(MAPPING_PATH)) {
            throw new NotSupportHandlerException();
        }

        var registerRequest = toRegisterRequest(request);
        if (registerRequest == null) {
            return toLoginPage();
        }
        register(registerRequest);

        return toLoginResponse();
    }

    private RegisterRequest toRegisterRequest(final HttpRequest request) {
        var account = request.getBodyValue("account");
        var password = request.getBodyValue("password");
        var email = request.getBodyValue("email");
        if (account == null || password == null || email == null || request.isGet()) {
            return null;
        }
        return new RegisterRequest(account, password, email);
    }

    private HttpResponse toLoginPage() {
        try {
            final var file = FileReader.read(REGISTER_PAGE);
            return new HttpResponse(new HttpFileBody(file));
        } catch (IOException e) {
            throw new NotSupportHandlerException();
        }
    }

    private HttpResponse toLoginResponse() {
        return new HttpResponse(new HttpPath(SUCCESS_PAGE));
    }

    private void register(final RegisterRequest request) {
        var user = new User(request.account(), request.password(), request.email());
        InMemoryUserRepository.save(user);
    }
}


record RegisterRequest(String account, String password, String email) {
}
