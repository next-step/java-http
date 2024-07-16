package org.apache.coyote.http11;

import java.nio.charset.Charset;

public record HttpHeaders(MimeType mimeType, Charset charset, Location location) {

    public HttpHeaders(MimeType mimeType) {
        this(mimeType, Charset.defaultCharset(), null);
    }

    public HttpHeaders(Location location) {
        this(MimeType.ALL, Charset.defaultCharset(), location);
    }

    public HttpHeaders(MimeType mimeType, Location location) {
        this(mimeType, Charset.defaultCharset(), location);
    }

    public String toMessage() {
        String message = "Content-Type: " + mimeType.getDescription() + ";charset=" + charset.name().toLowerCase() + " \r\n";
        if (location != null) {
            message += "Location: " + location.url() + " \r\n";
        }
        return message;
    }

}
