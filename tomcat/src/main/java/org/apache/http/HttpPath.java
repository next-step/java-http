package org.apache.http;

public record HttpPath(
        String value
) {

    @Override
    public String toString() {
        return value;
    }
}
