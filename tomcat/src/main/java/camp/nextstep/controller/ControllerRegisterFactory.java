package camp.nextstep.controller;

import java.util.List;
import camp.nextstep.controller.strategy.NotFoundStrategy;
import camp.nextstep.controller.strategy.RequestMethodStrategy;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class ControllerRegisterFactory implements ControllerFactory {
    private final List<RequestMethodStrategy> requestMethods;

    public ControllerRegisterFactory(List<RequestMethodStrategy> requestMethods) {
        this.requestMethods = requestMethods;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        RequestMethodStrategy requestMethodStrategy = requestMethods.stream()
            .filter(method -> method.matched(httpRequest))
            .findFirst()
            .orElseGet(NotFoundStrategy::new);

        return requestMethodStrategy.serve(httpRequest);
    }
}
