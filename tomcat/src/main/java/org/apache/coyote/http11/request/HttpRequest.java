package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.http11.request.header.Cookie;
import org.apache.coyote.http11.request.requestline.HttpVersion;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.request.requestline.RequestProtocol;
import org.apache.coyote.http11.request.requestline.RequestUrl;

public abstract class HttpRequest {

    final RequestMethod requestMethod;
    final RequestUrl requestUrl;
    final RequestProtocol protocol;
    final HttpVersion version;
    final Map<String, String> params;
    final RequestBody requestBody;
    final Cookie cookie;

    public HttpRequest(RequestMethod requestMethod, RequestUrl requestUrl, RequestProtocol protocol,
        HttpVersion version, Map<String, String> params, RequestBody requestBody, Cookie cookie) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.protocol = protocol;
        this.version = version;
        this.params = params;
        this.requestBody = requestBody;
        this.cookie = cookie;
    }

    public String getRequestMethod() {
        return requestMethod.name();
    }

    public String getRequestUrl() {
        return requestUrl.getRequestUrl();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return version.getVersion();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Optional<Map<String, String>> getSession() {
        if (Objects.nonNull(cookie)) {
            return Optional.ofNullable(cookie.getSession());
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
            "requestMethod=" + requestMethod +
            ", requestUrl=" + requestUrl +
            ", protocol=" + protocol +
            ", version=" + version +
            ", params=" + params +
            ", requestBody=" + requestBody +
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
        HttpRequest that = (HttpRequest) o;
        return requestMethod == that.requestMethod && Objects.equals(requestUrl,
            that.requestUrl) && Objects.equals(protocol, that.protocol)
            && Objects.equals(version, that.version) && Objects.equals(params,
            that.params) && Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, requestUrl, protocol, version, params, requestBody);
    }
}
