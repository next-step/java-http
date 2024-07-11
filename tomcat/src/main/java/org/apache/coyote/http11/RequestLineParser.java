package org.apache.coyote.http11;

import org.apache.coyote.Parser;

public class RequestLineParser implements Parser {

    public static final String SPACE = " ";

    @Override
    public HttpRequest parse(String requestLine) {
        String[] parts = requestLine.split(SPACE);
        final HttpMethod httpMethod = HttpMethod.from(parts[0]);
        final RequestTarget requestTarget = RequestTarget.from(parts[1], parts[2]);

        return new HttpRequest(httpMethod, requestTarget);
    }
}
