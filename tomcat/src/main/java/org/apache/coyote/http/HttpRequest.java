package org.apache.coyote.http;

import org.apache.coyote.http11.HttpParseException;

import java.util.Map;

public class HttpRequest {

    private static final int REQUEST_LINE_NUMBERS = 3;
    private static final String REQUEST_LINE_SEPARATOR = " ";

    private final HttpRequestLine httpRequestLine = new HttpRequestLine();
    private final ParamsMapping params = new ParamsMapping();

    public void setRequestLine(final String requestLine) throws HttpParseException {
        final String[] requestLineMetaData = requestLine.split(REQUEST_LINE_SEPARATOR);

        if (requestLineMetaData.length < REQUEST_LINE_NUMBERS) {
            throw new HttpParseException("Invalid request line: " + requestLine);
        }

        this.httpRequestLine.setMethod(requestLineMetaData[0]);
        this.httpRequestLine.setProtocol(requestLineMetaData[2]);

        setPath(requestLineMetaData);
    }

    private void setPath(final String[] requestLineMetaData) {
        final String path = requestLineMetaData[1];
        final String[] requestPath = path.split(HttpRequestLine.REQUEST_PATH_QUERY_SEPARATOR);

        httpRequestLine.setPath(requestPath[0]);
        setQueryString(requestPath);
    }

    private void setQueryString(final String[] requestPath) {
        if (httpRequestLine.getMethod() != HttpMethod.GET) {
            return;
        }

        if (requestPath.length == 1) {
            return;
        }

        this.params.toQueryStringMapping(requestPath[1]);
    }

    public HttpMethod getMethod() {
        return this.httpRequestLine.getMethod();
    }

    public String getPath() {
        return this.httpRequestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return this.params.getParams();
    }

    public String getParameter(final String parameterName) {
        return this.params.getParam(parameterName);
    }

    public String getProtocol() {
        return this.httpRequestLine.getProtocol();
    }

    public String getProtocolVersion() {
        return this.httpRequestLine.getProtocolVersion();
    }
}
