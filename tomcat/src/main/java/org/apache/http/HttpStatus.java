package org.apache.http;

public enum HttpStatus {
    OK(200);

    public final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return statusCode + " " + name();
    }
}
