package camp.nextstep.request;

import camp.nextstep.request.handler.LoginHandler;
import camp.nextstep.request.handler.RegisterHandler;
import org.apache.coyote.http11.request.RequestHandlerMapper;

public class RequestHandlerInitializer {

    public static void init() {
        final RequestHandlerMapper requestHandlerMapper = RequestHandlerMapper.getInstance();
        requestHandlerMapper.addHandler("/register.html", new RegisterHandler());
        requestHandlerMapper.addHandler("/login.html", new LoginHandler());
    }
}
