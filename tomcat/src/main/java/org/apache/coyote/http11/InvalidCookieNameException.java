package org.apache.coyote.http11;

public class InvalidCookieNameException extends IllegalArgumentException {
    public InvalidCookieNameException() {
        super("Cookie의 이름은 null일 수 없습니다.");
    }
}
