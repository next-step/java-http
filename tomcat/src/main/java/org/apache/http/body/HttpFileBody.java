package org.apache.http.body;

import org.apache.file.FileResource;
import org.apache.http.header.ContentLength;
import org.apache.http.header.ContentType;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.header.HttpResponseHeaders;

public class HttpFileBody extends HttpBody {
    private final FileResource file;

    public HttpFileBody(FileResource file) {
        this.file = file;
    }

    public HttpResponseHeaders addContentHeader(final HttpResponseHeaders headers) {
        return headers.add(new ContentType(file.mediaType()))
                .add(new ContentLength(file.contentSize()));
    }

    @Override
    public String toString() {
        return file.content();
    }
}
