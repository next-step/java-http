package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String DELIMITER = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";
    private final Map<String, String> headers;


    public RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader from(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        do {
            String line = br.readLine();
            if (!line.contains(DELIMITER)) {
                break;
            }
            String[] tokens = line.split(DELIMITER);
            headers.put(tokens[KEY_INDEX], tokens[VALUE_INDEX]);
        } while (br.ready());

        return new RequestHeader(headers);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, "0"));
    }

    public String getCookies() {
        return headers.getOrDefault(COOKIE, "");
    }
}
