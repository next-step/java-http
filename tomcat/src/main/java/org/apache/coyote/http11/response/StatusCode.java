package org.apache.coyote.http11.response;

public enum StatusCode {
    OK("200");

    private final String statusCode;

    StatusCode(String number) {
        this.statusCode = number;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return this.statusCode + " " + this.name();
    }
}
