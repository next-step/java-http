package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.HttpParams;
import org.apache.http.HttpPath;
import org.apache.http.HttpProtocol;

public class HttpRequestLine {
    private static final String URL_REGEX = "\\?";
    private static final String URL_DELIMITER = "\\?";

    protected final HttpMethod method;
    protected final HttpPath path;
    protected final HttpParams params;
    protected final HttpProtocol protocol;

    public HttpRequestLine(final String requestLine) {
        final var tokens = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tokens[0]);
        var urlTokens = tokens[1].split(URL_REGEX);
        this.path = new HttpPath(urlTokens[0]);
        this.params = new HttpParams((urlTokens.length > 1) ? urlTokens[1] : "");
        this.protocol = new HttpProtocol(tokens[2]);
    }

    @Override
    public String toString() {
        return method + " " + path + URL_DELIMITER + params + " " + protocol;
    }
}
