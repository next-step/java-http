package org.apache.coyote.http;

import org.apache.coyote.http11.HttpParseException;

import java.util.Map;
import java.util.Objects;

public class HttpRequest {

    private static final int REQUEST_LINE_NUMBERS = 3;
    private static final String REQUEST_LINE_SEPARATOR = " ";
    private static final int REQUEST_LINE_METHOD_POINT = 0;
    private static final int REQUEST_LINE_PROTOCOL_POINT = 2;
    private static final int REQUEST_LINE_PATH_POINT = 1;
    private static final String EMPTY_CONTENT_LENGTH = "0";

    private final HttpRequestLine httpRequestLine = new HttpRequestLine();
    private final HeaderMapping headerMapping = new HeaderMapping();
    private final ParamsMapping params = new ParamsMapping();
    private String body = "";

    public void setRequestLine(final String requestLine) throws HttpParseException {
        final String[] requestLineMetaData = requestLine.split(REQUEST_LINE_SEPARATOR);

        if (requestLineMetaData.length < REQUEST_LINE_NUMBERS) {
            throw new HttpParseException("Invalid request line: " + requestLine);
        }

        this.httpRequestLine.setMethod(requestLineMetaData[REQUEST_LINE_METHOD_POINT]);
        this.httpRequestLine.setProtocol(requestLineMetaData[REQUEST_LINE_PROTOCOL_POINT]);

        setPath(requestLineMetaData);
    }

    private void setPath(final String[] requestLineMetaData) {
        final String path = requestLineMetaData[REQUEST_LINE_PATH_POINT];
        final String[] requestPath = path.split(HttpRequestLine.REQUEST_PATH_QUERY_SEPARATOR);

        httpRequestLine.setPath(requestPath[REQUEST_LINE_METHOD_POINT]);
        setQueryString(requestPath);
    }

    private void setQueryString(final String[] requestPath) {
        if (httpRequestLine.getMethod() != HttpMethod.GET) {
            return;
        }

        if (requestPath.length == REQUEST_LINE_PATH_POINT) {
            return;
        }

        this.params.fromQueryString(requestPath[REQUEST_LINE_PATH_POINT]);
    }

    public void appendHeader(final String name, final String... value) {
        final HttpHeader headerName = HttpHeader.from(name);

        if (Objects.isNull(headerName)) {
            return;
        }

        this.headerMapping.addHeader(headerName, value);
    }

    public void setBody(final String body) {
        if (headerMapping.isFormUrlEncoded()) {
            params.fromBodyForm(body);
        }
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

    public boolean isContentLengthEmpty() {
        final String[] contentLengthHeaders = this.headerMapping.getHeader(HttpHeader.CONTENT_LENGTH);

        if (Objects.isNull(contentLengthHeaders) || contentLengthHeaders.length == REQUEST_LINE_METHOD_POINT) {
            return true;
        }

        return Objects.equals(contentLengthHeaders[REQUEST_LINE_METHOD_POINT], EMPTY_CONTENT_LENGTH);
    }

    public int getBodyLength() {
        return Integer.parseInt(this.headerMapping.getHeader(HttpHeader.CONTENT_LENGTH)[REQUEST_LINE_METHOD_POINT]);
    }
}
