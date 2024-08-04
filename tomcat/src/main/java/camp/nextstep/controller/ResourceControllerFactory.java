package camp.nextstep.controller;

import camp.nextstep.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.controller.ControllerFactory;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class ResourceControllerFactory implements ControllerFactory {

    private final RequestMethodStrategy resourceStrategy;

    public ResourceControllerFactory(RequestMethodStrategy resourceStrategy) {
        this.resourceStrategy = resourceStrategy;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return resourceStrategy.serve(httpRequest);
    }

}


