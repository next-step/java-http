package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpPath;
import org.apache.coyote.http11.model.RequestLine;
import org.apache.coyote.http11.model.constant.HttpMethod;

import java.util.HashMap;

public class RequestLineParser {
    private static final String BLANK = " ";
    private static final String SLASH = "/";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private static final String BACKSLASH = "\\";

    public RequestLine parse(final String requestLine) {
        final String[] tokens = tokenize(requestLine, BLANK);
        final HashMap<String, String> queryParams = new HashMap<>();
        final String httpMethod = tokens[0];
        final String path = parsePath(tokens[1], queryParams);

        final String[] protocolAndVersion = tokenize(tokens[2], SLASH);
        final String protocol = protocolAndVersion[0];
        final String version = protocolAndVersion[1];

        return new RequestLine(
                HttpMethod.valueOf(httpMethod),
                HttpPath.of(path, queryParams),
                protocol,
                version
        );
    }

    private String parsePath(final String path, final HashMap<String, String> queryParams) {
        if (hasNotQueryString(path)) {
            return path;
        }

        final String[] pathTokens = tokenize(path, BACKSLASH + QUESTION_MARK);
        final String[] queryStringTokens = tokenize(pathTokens[1], AMPERSAND);

        for (String queryString : queryStringTokens) {
            final String[] keyValueTokens = tokenize(queryString, EQUAL_SIGN);
            queryParams.put(keyValueTokens[0], keyValueTokens[1]);
        }

        return pathTokens[0];
    }

    private boolean hasNotQueryString(final String path) {
        return !path.contains(QUESTION_MARK);
    }

    private String[] tokenize(String input, String delimiter) {
        return StringTokenizer.token(input, delimiter);
    }
}
