package org.apache.coyote.http;

import org.apache.coyote.http11.HttpParseException;

import java.util.Map;

public class Request {

    private static final int REQUEST_LINE_NUMBERS = 3;
    private static final String REQUEST_LINE_SEPARATOR = " ";

    private final RequestLine requestLine = new RequestLine();
    private final ParamsMapping params = new ParamsMapping();

    public void setRequestLine(final String requestLine) throws HttpParseException {
        final String[] requestLineMetaData = requestLine.split(REQUEST_LINE_SEPARATOR);

        if (requestLineMetaData.length < REQUEST_LINE_NUMBERS) {
            throw new HttpParseException("Invalid request line: " + requestLine);
        }

        this.requestLine.setMethod(requestLineMetaData[0]);
        this.requestLine.setProtocol(requestLineMetaData[2]);

        setPath(requestLineMetaData);
    }

    private void setPath(final String[] requestLineMetaData) {
        final String path = requestLineMetaData[1];
        final String[] requestPath = path.split(RequestLine.REQUEST_PATH_QUERY_SEPARATOR);

        requestLine.setPath(requestPath[0]);
        setQueryString(requestPath);
    }

    private void setQueryString(final String[] requestPath) {
        if (requestLine.getMethod() != HttpMethod.GET) {
            return;
        } else if (requestPath.length == 1) {
            return;
        }

        this.params.toQueryStringMapping(requestPath[1]);
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return this.params.getParams();
    }

    public String getParameter(final String parameterName) {
        return this.params.getParam(parameterName);
    }

    public String getProtocol() {
        return this.requestLine.getProtocol();
    }

    public String getProtocolVersion() {
        return this.requestLine.getProtocolVersion();
    }
}
