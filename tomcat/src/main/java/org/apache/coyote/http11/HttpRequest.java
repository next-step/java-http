package org.apache.coyote.http11;

public record HttpRequest(
        HttpMethod httpMethod,
        RequestTarget requestTarget
) {

    // 객체 안의 객체가 depth 로 겹겹이 되어있다보니 이런 방식을 고민하게 되었습니다
    // 내부호출되도록 메세지를 던져서 이런식으로 리턴하는 것도 객체의 책임을 위임하는 코드라고 생각하는데,
    // 예를 들어, 프로토콜을 값객체로 만들어놓고 String 을 반환하는것이 맞는것인지 의문인 듭니다.
    // 값객체를 반환하는게 맞는걸까요? 그렇다면 또 굳이 이런 메서드를 만들필요가 없다는 느낌이 듭니다..
//    public String getProtocol() {
//        return requestTarget.protocol().value();
//    }
//
//    public String getProtocolVersion() {
//        return requestTarget.protocolVersion().value();
//    }
//
//    public String getQueryParam(String key) {
//        return requestTarget.queryParamsMap().value().get(key);
//    }
}
