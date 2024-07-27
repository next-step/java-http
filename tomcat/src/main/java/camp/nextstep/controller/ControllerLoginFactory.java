package camp.nextstep.controller;

import camp.nextstep.controller.strategy.NotFoundStrategy;
import camp.nextstep.controller.strategy.RequestMethodStrategy;
import camp.nextstep.controller.strategy.UnauthorizedStrategy;
import org.apache.coyote.http11.exception.UnAuthorizedException;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import java.util.List;

public class ControllerLoginFactory implements ControllerFactory {

    private final List<RequestMethodStrategy> requestMethods;
    private final UnauthorizedStrategy redirectStrategy = new UnauthorizedStrategy();

    public ControllerLoginFactory(List<RequestMethodStrategy> requestMethods) {
        this.requestMethods = requestMethods;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        RequestMethodStrategy requestMethodStrategy = requestMethods.stream()
                .filter(method -> method.matched(httpRequest))
                .findFirst()
                .orElseGet(NotFoundStrategy::new);

        try {
            return requestMethodStrategy.serve(httpRequest);
        } catch (UnAuthorizedException e) {
            return redirectStrategy.serve(httpRequest);
        }

    }
}
