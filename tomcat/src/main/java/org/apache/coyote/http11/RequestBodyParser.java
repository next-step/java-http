package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class RequestBodyParser {

    public static final String AMPERSAND = "&";
    public static final String EQUAL_SIGN_DELIMITER = "=";
    public static final String EMPTY_STRING = "";

    private RequestBodyParser() {
    }

    public static RequestBody parse(final BufferedReader br, final HttpHeaders headers) throws IOException {
        String bodyString = parseToString(br, headers);

        Map<String, String> bodyMap = new HashMap<>();
        String[] pairs = bodyString.split(AMPERSAND);
        for (String pair : pairs) {
            putBodyMap(pair, bodyMap);
        }

        return new RequestBody(bodyMap);
    }

    private static void putBodyMap(String pair, Map<String, String> bodyMap) {
        String[] keyValue = pair.split(EQUAL_SIGN_DELIMITER, 2);
        if (keyValue.length == 2) {
            String key = keyValue[0];
            String value = keyValue[1];
            bodyMap.put(key, value);
        }
    }

    private static String parseToString(BufferedReader br, HttpHeaders headers) throws IOException {
        if (headers.hasContentLength()) {
            final int contentLength = headers.contentLength();

            final char[] body = new char[contentLength];
            br.read(body);

            return new String(body);
        }

        return EMPTY_STRING;
    }
}
