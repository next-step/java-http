package camp.nextstep.controller;

import camp.nextstep.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class ControllerResourceFactory implements ControllerFactory {

    private final RequestMethodStrategy resourceStrategy;

    public ControllerResourceFactory(RequestMethodStrategy resourceStrategy) {
        this.resourceStrategy = resourceStrategy;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return resourceStrategy.serve(httpRequest);
    }

}


