package org.apache.http.body;

import org.apache.http.header.ContentLength;
import org.apache.http.header.ContentType;
import org.apache.http.header.HttpHeaders;
import org.apache.http.header.MediaType;

public class HttpTextBody extends HttpBody {
    private final String body;

    public HttpTextBody(String body) {
        this.body = body;
    }

    @Override
    public HttpHeaders addContentHeader(HttpHeaders headers) {
        return headers.add(new ContentType(MediaType.TEXT_HTML))
                .add(new ContentLength(body.getBytes().length));
    }

    @Override
    public String toString() {
        return body;
    }
}
