package org.apache.coyote.http11;

public class RequestLine {
    private static final String REQUEST_LINE_SEPARATOR = " ";

    private final HttpMethod method;
    private final RequestTarget requestTarget;
    private final HttpProtocol httpProtocol;

    private RequestLine(
            final HttpMethod method,
            final RequestTarget requestTarget,
            final HttpProtocol httpProtocol
    ) {
        this.method = method;
        this.requestTarget = requestTarget;
        this.httpProtocol = httpProtocol;
    }

    public static RequestLine from(final String requestLine) {
        String[] requestLineElements = requestLine.split(REQUEST_LINE_SEPARATOR);

        HttpMethod method = HttpMethod.from(requestLineElements[0]);
        RequestTarget requestTarget = RequestTarget.from(requestLineElements[1]);
        HttpProtocol httpProtocol = HttpProtocol.from(requestLineElements[2]);

        return new RequestLine(method, requestTarget, httpProtocol);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return requestTarget.getPath();
    }

    public String getVersion() {
        return httpProtocol.getVersion();
    }

    public HttpProtocol getProtocol() {
        return httpProtocol;
    }

    public boolean hasQueryParams() {
        return requestTarget.hasQueryParams();
    }

    public String getQueryParamValue(final String queryParamKey) {
        return requestTarget.getQueryParamValue(queryParamKey);
    }
}
