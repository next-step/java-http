package org.apache.coyote.http11.response.statusline;

public enum StatusCode {
    OK("200"),
    NOTFOUND("404"),
    FOUND("302"),
    UNAUTHORIZED("302");

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
