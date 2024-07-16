package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeaders {

    private static final String HOST = "Host";

    private final Map<String, Object> headers = new HashMap<>();

    public HttpRequestHeaders(List<String> lines) {
        if (lines.isEmpty()) {
            return;
        }
        for (String line : lines) {
            parseField(line);
        }
    }

    public String host() {
        return (String) headers.get(HOST);
    }


    private void parseField(String line) {
        String[] arr = line.split(": ");
        headers.put(arr[0], arr[1]);
    }

    public int contentLength() {
        return Integer.parseInt((String) headers.getOrDefault("Content-Length", "0"));
    }
}
