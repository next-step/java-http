package org.apache.coyote.http11.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class Controller404Factory implements ControllerFactory {

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return null;
    }
}
