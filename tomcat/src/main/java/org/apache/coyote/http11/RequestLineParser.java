package org.apache.coyote.http11;

import org.apache.coyote.http11.model.RequestLine;
import org.apache.coyote.http11.model.constant.HttpMethod;

public class RequestLineParser {

    private static final String BLANK = " ";
    private static final String SLASH = "/";

    public RequestLine parse(final String requestLine) {
        final String[] tokens = tokenize(requestLine, BLANK);
        final String httpMethod = tokens[0];
        final String path = tokens[1];

        final String[] protocolAndVersion = tokenize(tokens[2], SLASH);
        final String protocol = protocolAndVersion[0];
        final String version = protocolAndVersion[1];

        return new RequestLine(
                HttpMethod.valueOf(httpMethod),
                path,
                protocol,
                version
        );
    }

    private String[] tokenize(String input, String delimiter) {
        return StringTokenizer.token(input, delimiter);
    }
}
