package org.apache.coyote.http11.request.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.QueryParams;
import org.apache.coyote.http11.model.RequestLine;
import org.apache.coyote.http11.model.constant.HttpMethod;

import java.io.IOException;

public class RegisterHandler extends AbstractRequestHandler {

    private static final String POST_METHOD_REDIRECT_PATH = "/index.html";
    private static final String OTHER_METHOD_REDIRECT_PATH = "/404.html";
    private static final String ACCOUNT_KEY = "account";
    private static final String PASSWORD_KEY = "password";
    private static final String EMAIL_KEY = "email";

    @Override
    public String handle(final HttpRequest request) throws IOException {
        final RequestLine requestLine = request.httpRequestHeader()
                .requestLine();
        final HttpMethod httpMethod = requestLine.httpMethod();

        if (httpMethod.isGetMethod()) {
            final String body = buildBodyFromReadFile(requestLine.url());
            return buildHttpOkResponse(body, requestLine.contentTypeText());
        }

        if (request.hasRequestBody()) {
            saveUser(request.requestBody());
            return buildRedirectResponse(POST_METHOD_REDIRECT_PATH);
        }

        return buildRedirectResponse(OTHER_METHOD_REDIRECT_PATH);
    }

    private void saveUser(final QueryParams requestBody) {
        InMemoryUserRepository.save(
                new User(
                        requestBody.valueBy(ACCOUNT_KEY),
                        requestBody.valueBy(PASSWORD_KEY),
                        requestBody.valueBy(EMAIL_KEY)
                )
        );
    }
}
