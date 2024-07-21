package org.apache.coyote.http11.controller;

import java.util.List;
import org.apache.coyote.http11.controller.strategy.NotFoundStrategy;
import org.apache.coyote.http11.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class ControllerIndexFactory implements ControllerFactory {

    private final List<RequestMethodStrategy> requestMethods;

    public ControllerIndexFactory(List<RequestMethodStrategy> requestMethods) {
        this.requestMethods = requestMethods;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        RequestMethodStrategy requestMethodStrategy = requestMethods.stream()
            .filter(method -> method.matched(httpRequest.getRequestMethod()))
            .findFirst()
            .orElseGet(NotFoundStrategy::new);

        return requestMethodStrategy.serve(httpRequest);
    }
}
