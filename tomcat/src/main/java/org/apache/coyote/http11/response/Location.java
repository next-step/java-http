package org.apache.coyote.http11.response;

import org.apache.coyote.http11.HttpProtocol;

public record Location(String url) {

    public static Location of(HttpProtocol protocol, String host, String path) {
        return new Location(protocol.toUrlSyntax() + host + path);
    }
}