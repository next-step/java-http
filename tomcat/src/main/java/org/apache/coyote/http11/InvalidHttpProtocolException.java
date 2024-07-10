package org.apache.coyote.http11;

public class InvalidHttpProtocolException extends IllegalArgumentException {
    public InvalidHttpProtocolException() {
        super("HTTP가 아니거나 지원하지 않는 HTTP 버전입니다.");
    }
}
