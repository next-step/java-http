package org.apache.coyote.http11.request.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.QueryParams;
import org.apache.coyote.http11.model.RequestLine;

import java.io.IOException;
import java.util.NoSuchElementException;

public class LoginHandler extends AbstractRequestHandler {

    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";
    private static final String SUCCESS_REDIRECT_PATH = "/index.html";
    private static final String FAILED_REDIRECT_PATH = "/401.html";

    @Override
    public String handle(final HttpRequest request) throws IOException {
        final RequestLine requestLine = request.httpRequestHeader()
                .requestLine();
        final QueryParams queryParams = requestLine.queryParams();

        final String body = buildBodyFromReadFile(requestLine.url());

        if (queryParams.isEmpty()) {
            return buildHttpOkResponse(body, requestLine.contentTypeText());
        }

        final User user = InMemoryUserRepository.findByAccount(queryParams.valueBy(LOGIN_ACCOUNT_KEY))
                .orElseThrow(NoSuchElementException::new);

        if (user.checkPassword(queryParams.valueBy(LOGIN_PASSWORD_KEY))) {
            return buildRedirectResponse(SUCCESS_REDIRECT_PATH);
        }

        return buildRedirectResponse(FAILED_REDIRECT_PATH);
    }
}
