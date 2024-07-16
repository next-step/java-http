package org.apache.coyote.http11.response;

import org.apache.coyote.http11.MimeType;

import java.nio.charset.Charset;

public record HttpResponseHeaders(MimeType mimeType, Charset charset, Location location) {

    public HttpResponseHeaders(MimeType mimeType) {
        this(mimeType, Charset.defaultCharset(), null);
    }

    public HttpResponseHeaders(Location location) {
        this(MimeType.ALL, Charset.defaultCharset(), location);
    }

    public HttpResponseHeaders(MimeType mimeType, Location location) {
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
