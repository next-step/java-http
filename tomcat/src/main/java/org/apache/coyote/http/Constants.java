package org.apache.coyote.http;

public enum Constants {
    CR("\r"),
    LF("\n");

    private final String value;

    Constants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
