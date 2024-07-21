package org.apache.coyote.http11.controller;

import java.util.List;
import org.apache.coyote.http11.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;

public class ControllerDefaultFactory implements ControllerFactory {

    public ControllerDefaultFactory(List<RequestMethodStrategy> defaultGetStrategies) {
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String message = "Hello world!";
        return new Http11Response.HttpResponseBuilder()
            .statusLine("1.1", "OK")
            .responseHeader("text/html", message.getBytes().length)
            .messageBody(message.getBytes())
            .build();
    }
}
