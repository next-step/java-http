package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private HttpMethod method;
    private String path;
    private final Map<String, String> queryStringMapping = new HashMap<>();
    private String protocol;
    private String protocolVersion;

    public void setMethod(final String requestMethod) {
        this.method = HttpMethod.of(requestMethod);
    }

    public void setPath(final String path) {
        final String[] requestPath = path.split("\\?");

        this.path = requestPath[0];

        if (method != HttpMethod.GET) {
            return;
        }

        if (requestPath.length == 1) {
            return;
        }

        this.queryStringMapping.putAll(toQueryStringMapping(requestPath[1]));
    }

    private Map<String, String> toQueryStringMapping(final String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(param -> param.split("=", 2))
                .collect(Collectors.toMap(
                        pair -> pair[0],
                        pair -> pair.length > 1 ? pair[1] : ""
                ));
    }

    public void setProtocol(final String protocol) {
        final String[] protocolData = protocol.split("/");

        this.protocol = protocolData[0];
        this.protocolVersion = protocolData[1];
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Map<String, String> getQueryStringMapping() {
        return Collections.unmodifiableMap(this.queryStringMapping);
    }

    public String getParameter(final String parameterName) {
        return this.queryStringMapping.get(parameterName);
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }
}
