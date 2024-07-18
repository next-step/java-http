package camp.nextstep;

import camp.nextstep.controller.LoginHandler;
import camp.nextstep.controller.RegisterHandler;
import org.apache.coyote.http11.RequestMapper;
import org.apache.coyote.http11.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public final class RequestMappingAdapter {

    private static final Map<String, RequestHandler> handlers = new HashMap<>();

    static {
        handlers.put("/login", new LoginHandler());
        handlers.put("/register", new RegisterHandler());
        RequestMapper.addHandlers(handlers);
    }
}
