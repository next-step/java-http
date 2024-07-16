package org.apache.coyote.http11.request;

import java.util.List;

public class HttpRequestHeaders {

    private static final String HOST = "Host: ";

    private String host;

    public HttpRequestHeaders(List<String> lines) {
        if (lines.isEmpty()) {
            return;
        }
        for (String line : lines) {
            parseField(line);
        }
    }

    public String host() {
        return host;
    }


    private void parseField(String line) {
        if (line.startsWith(HOST)) {
            this.host = line.replace(HOST, "");
        }
    }

}
