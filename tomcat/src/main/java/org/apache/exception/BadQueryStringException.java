package org.apache.exception;

public class BadQueryStringException extends RuntimeException {
    public BadQueryStringException() {
        super("[ERROR] QueryString이 잘못 입력됐습니다.");
    }

    public BadQueryStringException(String message) {
        super(message);
    }
}
