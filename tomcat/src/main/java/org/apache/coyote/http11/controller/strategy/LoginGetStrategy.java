package org.apache.coyote.http11.controller.strategy;

import camp.nextstep.db.InMemoryUserRepository;
import java.util.Map;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.ProtocolVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginGetStrategy implements RequestMethodStrategy {

    private static final Logger log = LoggerFactory.getLogger(LoginGetStrategy.class);

    @Override
    public boolean matched(String requestMethod) {
        return requestMethod.equals("GET");
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        Map<String, String> userParams = httpRequest.getParams();
        String userLog = InMemoryUserRepository.findByAccount(userParams.get("account"))
            .orElseThrow(() -> new RuntimeException("Empty User 입니다.")).toString();
        log.info(userLog);
        return new Http11Response.HttpResponseBuilder()
            .statusLine(ProtocolVersion.HTTP11.getVersion(), "OK")
            .responseHeader(ContentType.html.getContentType(), userLog.getBytes().length)
            .messageBody(userLog.getBytes())
            .build();
    }
}
