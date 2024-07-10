package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.HttpParams;
import org.apache.http.HttpPath;
import org.apache.http.HttpProtocol;

public class HttpRequestLine {
    protected final HttpMethod method;
    protected final HttpPath path;
    protected final HttpParams params;
    protected final HttpProtocol protocol;

    public HttpRequestLine(final String requestLine) {
        final var tokens = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tokens[0]);
        this.path = HttpPath.fromUrl(tokens[1]);
        this.params = new HttpParams(tokens[1]);
        this.protocol = new HttpProtocol(tokens[2]);
    }

}
