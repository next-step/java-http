package org.apache.coyote.http11.request;

import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.cookie.Cookie;
import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.model.*;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestBodies requestBodies;
    private final Cookies cookies;

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBodies requestBodies, Cookies cookies) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBodies = requestBodies;
        this.cookies = cookies;
    }

    public String getUrlPath() {
        return requestLine.getUrlPath();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public Cookies getCookies() {
        return cookies;
    }

    public boolean hasCookies() {
        return cookies.isNotEmpty();
    }

    public String getRequestBodyValueByKey(String key) {
        return requestBodies.getRequestBodyValueByKey(key);
    }

    public String getExtension() {
        return requestLine.getExtension();
    }

    public boolean hasJSessionId() {
        return cookies.hasJSessionId();
    }

    public String getJSessionId() {
        return cookies.getJSessionId();
    }

    public void addCookie(final Cookie cookie) {
        cookies.addCookie(cookie);
    }

    public boolean isPost() {
        return HttpMethod.POST.equals(requestLine.getHttpMethod());
    }

    public boolean isGet() {
        return HttpMethod.GET.equals(requestLine.getHttpMethod());
    }
}
