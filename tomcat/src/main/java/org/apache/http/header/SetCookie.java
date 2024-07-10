package org.apache.http.header;

import org.apache.http.cookie.HttpCookie;

public class SetCookie implements HttpResponseHeader {

    private final HttpCookie cookie;

    public SetCookie(String cookie) {
        this.cookie = new HttpCookie(cookie);
    }

    @Override
    public String toString() {
        return "Set-Cookie" + DELIMITER + cookie;
    }
}
