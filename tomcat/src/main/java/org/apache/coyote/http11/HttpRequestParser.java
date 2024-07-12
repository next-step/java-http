package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.QueryParams;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequestParser {

    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int IS_KEY_VALUE_PAIR = 2;

    private HttpRequestParser() {
    }

    public static HttpRequestParser getInstance() {
        return HttpRequestParser.SingletonHelper.SINGLETON;
    }

    public HttpRequest parse(final HttpHeaders requestHeader, final String requestBodyLine) throws IOException {
        if (requestBodyLine != null && !requestBodyLine.isEmpty() && !requestBodyLine.isBlank()) {
            final Map<String, String> resultMap = parseRequestBody(requestBodyLine);
            final QueryParams queryParams = new QueryParams(resultMap);

            return new HttpRequest(requestHeader, queryParams);
        }

        return new HttpRequest(requestHeader, QueryParams.emtpy());
    }

    private Map<String, String> parseRequestBody(final String requestBodyString) {
        if (requestBodyString.isEmpty() || requestBodyString.isBlank()) {
            return Collections.emptyMap();
        }

        return Stream.of(StringTokenizer.token(requestBodyString, AMPERSAND))
                .map(pair -> StringTokenizer.token(pair, EQUAL_SIGN))
                .filter(keyValue -> keyValue.length == IS_KEY_VALUE_PAIR)
                .collect(Collectors.toUnmodifiableMap(
                                keyValue -> decode(keyValue[KEY_INDEX]),
                                keyValue -> decode(keyValue[VALUE_INDEX])
                        )
                );
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static class SingletonHelper {
        private static final HttpRequestParser SINGLETON = new HttpRequestParser();
    }
}
