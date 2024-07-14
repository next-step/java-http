package camp.nextstep.controller;

import org.apache.coyote.http11.RequestHandlerMapping;

public class RequestConfig {
    private final RequestHandlerMapping requestHandlerMapping;

    public RequestConfig(final RequestHandlerMapping requestHandlerMapping) {
        this.requestHandlerMapping = requestHandlerMapping;
    }

    public void register() {
        requestHandlerMapping.register("/login", new LoginController());
        requestHandlerMapping.register("/register", new RegisterController());
        requestHandlerMapping.register("/", new IndexController());
    }
}
