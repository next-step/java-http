package camp.nextstep.http.config;

import camp.nextstep.http.handler.*;
import camp.nextstep.service.UserService;

import java.util.List;

public class ServerStartUpConfig {
    private static final UserService userService = new UserService();
    private static final SessionHandler sessionHandler = new SessionHandler();

    private static final RootHttpRequestHandler rootHttpRequestHandler
            = new RootHttpRequestHandler();

    private static final LoginHttpRequestHandler loginHttpRequestHandler
            = new LoginHttpRequestHandler(userService, sessionHandler);

    private static final RegisterHttpRequestHandler registerHttpRequestHandler
            = new RegisterHttpRequestHandler();

    private static final FileHttpRequestHandler fileHttpRequestHandler
            = new FileHttpRequestHandler();

    private static final HttpRequestHandlerContainer httpRequestHandlerContainer
            = new HttpRequestHandlerContainer(
                    List.of(
                            rootHttpRequestHandler,
                            loginHttpRequestHandler,
                            registerHttpRequestHandler,
                            fileHttpRequestHandler
                    )
    );

    public static HttpRequestHandlerContainer getHttpRequestHandlerContainer() {
        return httpRequestHandlerContainer;
    }
}
