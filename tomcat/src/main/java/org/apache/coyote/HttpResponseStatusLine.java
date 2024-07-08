package org.apache.coyote;

public record HttpResponseStatusLine(String protocol, String protocolVersion, Integer statusCode, String statusText) {
    @Override
    public String toString() {
        return protocol + "/" + protocolVersion + " " + statusCode + " " + statusText + " ";
    }
}
