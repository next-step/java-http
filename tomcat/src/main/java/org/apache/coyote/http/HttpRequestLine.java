package org.apache.coyote.http;

public class HttpRequestLine {
    public static final String REQUEST_PATH_QUERY_SEPARATOR = "\\?";
    private static final String REQUEST_PROTOCOL_SEPARATOR = "/";

    private HttpMethod method;
    private String path;
    private String protocol;
    private String protocolVersion;

    public void setMethod(final String requestMethod) {
        this.method = HttpMethod.from(requestMethod);
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setProtocol(final String protocol) {
        final String[] protocolData = protocol.split(REQUEST_PROTOCOL_SEPARATOR);

        this.protocol = protocolData[0];
        this.protocolVersion = protocolData[1];
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

}
