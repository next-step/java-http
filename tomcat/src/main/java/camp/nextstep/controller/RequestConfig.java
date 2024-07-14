package camp.nextstep.controller;

import org.apache.coyote.http11.RequestHandler;

public class RequestConfig {
    private final RequestHandler requestHandler;

    public RequestConfig(final RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void register() {
        requestHandler.register("/login", new LoginController());
        requestHandler.register("/register", new RegisterController());
        requestHandler.register("/", new IndexController());
    }
}
