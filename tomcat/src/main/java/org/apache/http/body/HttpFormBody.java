package org.apache.http.body;

import org.apache.file.MediaType;
import org.apache.http.HttpParams;
import org.apache.http.header.ContentLength;
import org.apache.http.header.ContentType;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.header.HttpResponseHeaders;

public class HttpFormBody extends HttpBody {
    private final HttpParams params;

    public HttpFormBody(final String body) {
        params = new HttpParams(body);
    }

    @Override
    public HttpResponseHeaders addContentHeader(HttpResponseHeaders headers) {
        return headers.add(new ContentType(MediaType.FORM_URL_ENCODED))
                .add(new ContentLength(params.toString().getBytes().length));
    }

    @Override
    public String getValue(String key) {
        return params.get(key);
    }

    @Override
    public String toString() {
        return params.toString();
    }
}
