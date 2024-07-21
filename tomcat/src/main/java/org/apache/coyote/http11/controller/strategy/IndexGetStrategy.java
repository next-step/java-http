package org.apache.coyote.http11.controller.strategy;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class IndexGetStrategy implements RequestMethodStrategy {

    @Override
    public boolean matched(String requestMethod) {
        return false;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return null;
    }
}
