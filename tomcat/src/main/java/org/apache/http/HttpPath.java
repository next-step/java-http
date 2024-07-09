package org.apache.http;

public record HttpPath(
        String value
) {

    public static HttpPath fromUrl(final String url) {
        return new HttpPath(url.split("\\?")[0]);
    }

    @Override
    public String toString() {
        return value;
    }
}
