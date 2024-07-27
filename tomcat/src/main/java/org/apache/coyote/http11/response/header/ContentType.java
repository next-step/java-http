package org.apache.coyote.http11.response.header;

public enum ContentType {
    js("application/javascript;charset=utf-8"),
    html("text/html;charset=utf-8"),
    css("text/css"),
    svg("image/svg+xml"),
    ttf("application/octet-stream"),
    otf("application/x-font-opentype"),
    woff("application/font-woff"),
    woff2("application/font-woff2"),
    ico("image/avif"),
    json("application/json"),
    all("*/*");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return String.format("Content-Type: %s ", contentType);
    }
}
