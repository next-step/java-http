package org.apache.coyote.http11.request;

import java.util.Map;
import java.util.Objects;

public abstract class HttpRequest {

    final RequestMethod requestMethod;
    final RequestUrl requestUrl;
    final RequestProtocol protocol;
    final HttpVersion version;
    final Map<String, String> params;

    public HttpRequest(RequestMethod requestMethod, RequestUrl requestUrl, RequestProtocol protocol,
        HttpVersion version, Map<String, String> params) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.protocol = protocol;
        this.version = version;
        this.params = params;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    public RequestProtocol getProtocol() {
        return protocol;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Http11Request{" +
            "requestMethod=" + requestMethod +
            ", requestUrl=" + requestUrl +
            ", protocol=" + protocol.getProtocol() +
            ", version=" + version +
            ", params=" + params +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Http11Request that = (Http11Request) o;
        return requestMethod == that.requestMethod && Objects.equals(requestUrl,
            that.requestUrl) && Objects.equals(protocol, that.protocol)
            && Objects.equals(version, that.version) && Objects.equals(params,
            that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, requestUrl, protocol, version, params);
    }

}
