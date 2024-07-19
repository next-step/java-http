package org.apache.coyote.http11.response;

import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;

import java.nio.charset.Charset;

public record HttpResponseHeaders(MimeType mimeType, Charset charset, Location location, HttpCookie cookie) {

    public HttpResponseHeaders(MimeType mimeType) {
        this(mimeType, Charset.defaultCharset(), null, null);
    }

    public HttpResponseHeaders(Location location) {
        this(MimeType.ALL, Charset.defaultCharset(), location, null);
    }

    public HttpResponseHeaders(MimeType mimeType, Location location) {
        this(mimeType, Charset.defaultCharset(), location, null);
    }

    public HttpResponseHeaders(MimeType mimeType, Location location, HttpCookie httpCookie) {
        this(mimeType, Charset.defaultCharset(), location, httpCookie);
    }

    public String toMessage() {
        String message = "Content-Type: " + mimeType.getDescription() + ";charset=" + charset.name().toLowerCase() + " \r\n";
        if (location != null) {
            message += "Location: " + location.url() + " \r\n";
        }
        if (cookie != null) {
            message += "Set-Cookie: " + cookie.toValue() + " \r\n";
        }
        return message;
    }

}
