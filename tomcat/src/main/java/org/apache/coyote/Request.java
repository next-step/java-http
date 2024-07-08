package org.apache.coyote;

import java.util.Map;

public class Request {
    private HttpMethod method;
    private String path;
    private String protocol;
    private String protocolVersion;
    private final ParamsMapping params = new ParamsMapping();

    public void setMethod(final String requestMethod) {
        this.method = HttpMethod.from(requestMethod);
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

        this.params.toQueryStringMapping(requestPath[1]);
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

    public Map<String, String> getParameters() {
        return this.params.getParams();
    }

    public String getParameter(final String parameterName) {
        return this.params.getParam(parameterName);
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }
}
