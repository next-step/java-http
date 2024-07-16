package org.apache.coyote.http11;

import java.nio.charset.Charset;

public record HttpHeaders(MimeType mimeType, Charset charset) {

    public HttpHeaders(MimeType mimeType) {
        this(mimeType, Charset.defaultCharset());
    }

    public String contentTypeField() {
        return "Content-Type: " + mimeType.getDescription() + ";charset=" + charset.name().toLowerCase();
    }
}
