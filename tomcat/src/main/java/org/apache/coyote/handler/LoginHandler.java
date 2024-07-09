package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements Handler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);
    private static final String MAPPING_PATH = "/login";
    private static final String STATIC_FILE_PATH = "/login.html";

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (!request.matchPath(MAPPING_PATH)) {
            throw new NotSupportHandlerException();
        }
        login(request);
        request.proxyPath(STATIC_FILE_PATH);
        return null;
    }

    private void login(final HttpRequest request) {
        var account = request.getParam("account");
        var password = request.getParam("password");

        if (account.isEmpty() || password.isEmpty()) {
            return;
        }

        var user = InMemoryUserRepository.findByAccount(account.get()).orElse(null);
        if (user == null) {
            return;
        }
        if (user.checkPassword(password.get())) {
            log.info(user.toString());
        }
    }


}
