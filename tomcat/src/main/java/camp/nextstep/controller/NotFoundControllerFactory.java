package camp.nextstep.controller;

import camp.nextstep.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.controller.ControllerFactory;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class NotFoundControllerFactory implements ControllerFactory {

    private final RequestMethodStrategy notFoundStrategy;

    public NotFoundControllerFactory(RequestMethodStrategy notFoundStrategy) {
        this.notFoundStrategy = notFoundStrategy;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return notFoundStrategy.serve(httpRequest);
    }
}
