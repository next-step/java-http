package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.HttpParams;
import org.apache.http.HttpPath;
import org.apache.http.HttpProtocol;

public class HttpRequestLine {
    private static final String URL_REGEX = "\\?";
    private static final String URL_DELIMITER = "?";
    private static final String DELIMITER = " ";

    protected final HttpMethod method;
    protected final HttpPath path;
    protected final HttpParams params;
    protected final HttpProtocol protocol;

    public HttpRequestLine(final String requestLine) {
        try {
            final var tokens = requestLine.split(DELIMITER);
            this.method = HttpMethod.valueOf(tokens[0]);
            var urlTokens = tokens[1].split(URL_REGEX);
            this.path = new HttpPath(urlTokens[0]);
            this.params = parseParam(urlTokens);
            this.protocol = new HttpProtocol(tokens[2]);
        } catch (Exception e) {
            throw new NotSupportHttpRequestException();
        }
    }

    private HttpParams parseParam(String[] urlTokens) {
        if (urlTokens.length <= 1) {
            return null;
        }
        return new HttpParams(urlTokens[1]);
    }

    @Override
    public String toString() {
        return method + DELIMITER + path + (params != null ? URL_DELIMITER + params : "") + DELIMITER + protocol;
    }
}
