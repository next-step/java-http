package org.apache.coyote.http11;

public class ResourceNotFoundException extends IllegalArgumentException {
    public ResourceNotFoundException() {
        super("경로에 해당하는 리소스를 찾을 수 없습니다.");
    }
}
