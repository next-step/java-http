package camp.nextstep.controller;

import org.apache.coyote.HttpRequest;

public record RegisterRequest(String account, String password, String email) {

    public RegisterRequest(final HttpRequest request) {
        this(request.getBodyValue("account"), request.getBodyValue("password"), request.getBodyValue("email"));
        if (account == null || password == null || email == null) {
            throw new IllegalArgumentException();
        }
    }

}
