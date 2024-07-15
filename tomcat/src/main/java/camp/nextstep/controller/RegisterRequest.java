package camp.nextstep.controller;

import org.apache.coyote.HttpRequest;

public record RegisterRequest(String account, String password, String email) {

    public RegisterRequest(final HttpRequest request) {
        this(request.body.getValue("account"), request.body.getValue("password"), request.body.getValue("email"));
        if (account == null || password == null || email == null) {
            throw new IllegalArgumentException();
        }
    }

}
