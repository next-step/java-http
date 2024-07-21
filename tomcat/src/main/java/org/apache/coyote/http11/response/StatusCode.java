package org.apache.coyote.http11.response;

public enum StatusCode {
    OK(200);

    private final int statusCode;

    StatusCode(int number) {
        this.statusCode = number;
    }

    @Override
    public String toString() {
        return this.statusCode + " " + this.name();
    }
}
