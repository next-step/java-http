package camp.nextstep.http.config;

import camp.nextstep.http.handler.*;
import camp.nextstep.service.UserService;

import java.util.List;

public class ServerStartUpConfig {
    private final UserService userService = new UserService();
    private final SessionHandler sessionHandler = new SessionHandler();

    private final RootHttpRequestHandler rootHttpRequestHandler
            = new RootHttpRequestHandler();

    private final LoginHttpRequestHandler loginHttpRequestHandler
            = new LoginHttpRequestHandler(userService, sessionHandler);

    private final RegisterHttpRequestHandler registerHttpRequestHandler
            = new RegisterHttpRequestHandler();

    private final FileHttpRequestHandler fileHttpRequestHandler
            = new FileHttpRequestHandler();

    private final HttpRequestHandlerContainer httpRequestHandlerContainer
            = new HttpRequestHandlerContainer(
                    List.of(
                            rootHttpRequestHandler,
                            loginHttpRequestHandler,
                            registerHttpRequestHandler,
                            fileHttpRequestHandler
                    )
    );

    public HttpRequestHandlerContainer getHttpRequestHandlerContainer() {
        return httpRequestHandlerContainer;
    }
}
