package org.apache.coyote.handler;

import org.apache.coyote.HttpRequest;

public record LoginRequest(String account, String password) {

    public LoginRequest(final HttpRequest request) {
        this(request.getBodyValue("account"), request.getBodyValue("password"));
        if (account == null || password == null) {
            throw new NotSupportHandlerException();
        }
    }

}
