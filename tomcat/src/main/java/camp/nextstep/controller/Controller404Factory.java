package camp.nextstep.controller;

import camp.nextstep.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class Controller404Factory implements ControllerFactory {

    private final RequestMethodStrategy notFoundStrategy;

    public Controller404Factory(RequestMethodStrategy notFoundStrategy) {
        this.notFoundStrategy = notFoundStrategy;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return notFoundStrategy.serve(httpRequest);
    }
}
