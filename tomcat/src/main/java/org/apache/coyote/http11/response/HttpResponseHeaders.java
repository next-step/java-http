package org.apache.coyote.http11.response;

import jakarta.Cookie;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;
import org.apache.coyote.http11.constants.HttpFormat;

import java.nio.charset.Charset;

public class HttpResponseHeaders {

    private MimeType mimeType = MimeType.ALL;
    private Charset charset = Charset.defaultCharset();
    private Location location;
    private HttpCookie httpCookie = new HttpCookie();

    public HttpResponseHeaders() {
    }

    public HttpResponseHeaders(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void addMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.httpCookie.addCookie(cookie);
        }
    }

    public void addLocation(Location location) {
        if (location != null) {
            this.location = location;
        }
    }

    public String toMessage() {
        String message = HttpFormat.headerFieldValue(HttpFormat.HEADERS.CONTENT_TYPE, mimeType.getDescription() + ";charset=" + charset.name().toLowerCase());
        if (location != null) {
            message += HttpFormat.headerFieldValue(HttpFormat.HEADERS.LOCATION, location.url());
        }
        if (httpCookie.hasValues()) {
            message += HttpFormat.headerFieldValue(HttpFormat.HEADERS.COOKIE_RESPONSE_HEADER_FIELD, httpCookie.toString());
        }
        return message;
    }

}
