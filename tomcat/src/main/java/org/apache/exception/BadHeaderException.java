package org.apache.exception;

public class BadHeaderException extends RuntimeException{
    public BadHeaderException() {
        super("[ERROR] HttpHeader가 잘못 입력됐습니다.");
    }

    public BadHeaderException(String message) {
        super(message);
    }
}
