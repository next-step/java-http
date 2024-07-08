package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoginHandler implements Handler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);
    private static final String MAPPING_PATH = "/login";
    private static final String STATIC_FILE_PATH = "/login.html";

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (!Objects.equals(request.requestLine.path, MAPPING_PATH)) throw new NotSupportHandlerException();

        var params = request.requestLine.params;
        login(params.getOrDefault("account", ""), params.getOrDefault("password", ""));
        request.requestLine.path = STATIC_FILE_PATH;
        return null;
    }

    private void login(final String account, final String password) {
        var user = InMemoryUserRepository.findByAccount(account).orElse(null);
        if (user == null) return;
        if (user.checkPassword(password)) {
            log.info(user.toString());
        }
    }


}
