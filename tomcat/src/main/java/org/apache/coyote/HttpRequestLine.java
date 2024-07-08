package org.apache.coyote;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestLine {
    public RequestMethod method;
    public String path;
    public Map<String, String> params;
    public String protocol;
    public String protocolVersion;

    public HttpRequestLine(final String requestLine) {
        final var tokens = requestLine.split(" ");
        this.method = RequestMethod.valueOf(tokens[0]);
        this.path = tokens[1].split("\\?")[0];
        this.params = parseParams(tokens[1]);
        this.protocol = tokens[2].split("/")[0];
        this.protocolVersion = tokens[2].split("/")[1];
    }

    private Map<String, String> parseParams(final String input) {
        final var maps = new HashMap<String, String>();
        if (!input.contains("?")) return maps;
        final var params = input.split("\\?")[1].split("&");
        for (String param : params) {
            final String key = param.split("=")[0];
            final String value = param.split("=")[1];
            maps.put(key, value);
        }
        return maps;
    }

}
