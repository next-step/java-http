package org.apache.coyote.http11.response;

import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;
import org.apache.coyote.http11.constants.HttpFormat;

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
        String message = HttpFormat.headerFieldValue(HttpFormat.HEADERS.CONTENT_TYPE, mimeType.getDescription() + ";charset=" + charset.name().toLowerCase());
        if (location != null) {
            message += HttpFormat.headerFieldValue(HttpFormat.HEADERS.LOCATION, location.url());
        }
        if (cookie != null) {
            message += HttpFormat.headerFieldValue(HttpFormat.HEADERS.COOKIE_RESPONSE_HEADER_FIELD, cookie.toValue());
        }
        return message;
    }

}
