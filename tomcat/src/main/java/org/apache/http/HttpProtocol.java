package org.apache.http;

public record HttpProtocol(
        String protocol,
        String version
) {
    private static final String DELIMITER = "/";
    public static HttpProtocol HTTP_11 = new HttpProtocol("HTTP", "1.1");

    public HttpProtocol(final String protocols) {
        this(protocols.split(DELIMITER)[0], protocols.split(DELIMITER)[1]);
    }

    @Override
    public String toString() {
        return protocol + DELIMITER + version;
    }

}
