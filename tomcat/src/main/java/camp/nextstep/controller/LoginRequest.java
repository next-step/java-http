package camp.nextstep.controller;

import org.apache.coyote.HttpRequest;

public record LoginRequest(String account, String password) {

    public LoginRequest(final HttpRequest request) {
        this(request.body.getValue("account"), request.body.getValue("password"));
        if (account == null || password == null) {
            throw new IllegalArgumentException();
        }
    }

}
