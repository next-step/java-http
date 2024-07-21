package org.apache.coyote.http11.controller;

import java.util.List;
import org.apache.coyote.http11.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.ProtocolVersion;
import org.apache.coyote.http11.response.StatusCode;

public class ControllerDefaultFactory implements ControllerFactory {

    public ControllerDefaultFactory(List<RequestMethodStrategy> defaultGetStrategies) {
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String message = "Hello world!";

        return new Http11Response.HttpResponseBuilder()
            .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.OK.getStatusCode())
            .responseHeader(ContentType.html.getContentType(), message.getBytes().length)
            .messageBody(message.getBytes())
            .build();
    }
}
