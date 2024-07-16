package org.apache.coyote.http11;

public record Location(String url) {

    public static Location of(HttpProtocol protocol, String host, String path) {
        return new Location(protocol.toUrlSyntax() + host + path);
    }
}