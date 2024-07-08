package org.apache.coyote;

public enum HttpStatus {
    OK(200);

    public final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }
}
