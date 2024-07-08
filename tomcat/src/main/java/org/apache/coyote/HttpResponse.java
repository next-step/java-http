package org.apache.coyote;

import java.util.ArrayList;
import java.util.Map;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_responses
 */
public class HttpResponse {
    private final HttpResponseStatusLine statusLine;
    private final Map<String, String> responseHeaders;
    private final Map<String, String> representationHeaders;
    private final String body;

    public HttpResponse(HttpResponseStatusLine statusLine, Map<String, String> responseHeaders, Map<String, String> representationHeaders, String body) {
        this.statusLine = statusLine;
        this.responseHeaders = responseHeaders;
        this.representationHeaders = representationHeaders;
        this.body = body;
    }

    public byte[] toOutput() {
        var result = new ArrayList<String>();
        result.add(statusLine.toString());
        result.addAll(representationHeaders.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + " ").toList());
        result.addAll(responseHeaders.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue() + " ").toList());
        result.add("");
        result.add(body);

        return String.join("\r\n", result).getBytes();
    }
}
