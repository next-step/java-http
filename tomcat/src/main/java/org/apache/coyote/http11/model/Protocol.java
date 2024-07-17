package org.apache.coyote.http11.model;

public record Protocol(String value) {

    public static final String PROTOCOL_DELIMITER = "/";

    public String version() {
        return value.split(PROTOCOL_DELIMITER)[1];
    }
}
