package org.apache.coyote.http11.response;

public enum ProtocolVersion {
    HTTP11("HTTP", "1.1"),
    HTTP12("HTTP", "1.2");

    private String protocol;
    private String version;

    ProtocolVersion(String http, String version) {
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
