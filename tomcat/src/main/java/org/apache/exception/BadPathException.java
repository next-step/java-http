package org.apache.exception;

public class BadPathException extends RuntimeException {
    public BadPathException() {
        super("[ERROR] HttpPath가 잘못 입력됐습니다.");
    }

    public BadPathException(String message) {
        super(message);
    }
}
