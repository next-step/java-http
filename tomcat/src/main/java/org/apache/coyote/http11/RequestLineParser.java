package org.apache.coyote.http11;

import org.apache.coyote.Parser;

import java.util.HashMap;

public class RequestLineParser implements Parser {

    public static final String SPACE = " ";
    public static final String NEWLINE = "\n";
    public static final String COLON_DELIMITER = ":";

    @Override
    public HttpRequest parse(String requestLine) {
        String[] parts = requestLine.split(NEWLINE);
        final String[] firstLine = parts[0].split(SPACE); // httpMethod + httpPath + protocol
        final HttpMethod httpMethod = HttpMethod.from(firstLine[0]);
        final RequestTarget requestTarget = RequestTarget.from(firstLine[1], firstLine[2]);

        HttpHeaders httpHeaders = parseHeaders(parts);

        return new HttpRequest(httpMethod, requestTarget, httpHeaders);
    }

    private HttpHeaders parseHeaders(String[] parts) {
        HashMap<String, String> map = new HashMap<>();
        for(int i = 1; i < parts.length; i++) {
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
