package org.apache.http.cookie;

public class HttpCookie {
    private final String jsessionId;

    public HttpCookie(String sessionId) {
        this.jsessionId = sessionId;
    }

    @Override
    public String toString() {
        return jsessionId;
    }
}
