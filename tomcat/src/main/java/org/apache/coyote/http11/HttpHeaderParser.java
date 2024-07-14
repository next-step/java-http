package org.apache.coyote.http11;

import java.util.HashMap;

public final class HttpHeaderParser {

    public static final String LINE_BREAK = "\n";
    public static final String COLON_DELIMITER = ":";

    private HttpHeaderParser() {
    }

    public static HttpHeaders parse(final String requestLine) {
        String[] parts = requestLine.split(LINE_BREAK);

        HashMap<String, String> map = new HashMap<>();
        for (int i = 1; i < parts.length; i++) {
            int idx = parts[i].indexOf(COLON_DELIMITER);

            if (idx != -1) {
                String key = parts[i].substring(0, idx).trim();
                String value = parts[i].substring(idx + 1).trim();
                map.put(key, value);
            }
        }

        return new HttpHeaders(map);
    }
}
