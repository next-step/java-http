package org.apache.http.body;

import org.apache.file.FileResource;
import org.apache.http.header.ContentLength;
import org.apache.http.header.ContentType;
import org.apache.http.header.HttpHeaders;

public class HttpFileBody extends HttpBody {
    private final FileResource file;

    public HttpFileBody(FileResource file) {
        this.file = file;
    }

    public HttpHeaders addContentHeader(final HttpHeaders headers) {
        return headers.add(new ContentType(file.mediaType()))
                .add(new ContentLength(file.contentSize()));
    }

    @Override
    public String toString() {
        return file.content();
    }
}
