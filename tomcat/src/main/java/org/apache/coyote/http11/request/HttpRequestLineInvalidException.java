package org.apache.coyote.http11.request;

public class HttpRequestLineInvalidException extends RuntimeException {
    public HttpRequestLineInvalidException(String msg) {
        super(msg);
    }
}
