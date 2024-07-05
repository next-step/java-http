package camp.nextstep.domain.http;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    ;

    private final String requestKey;

    HttpMethod(String requestKey) {
        this.requestKey = requestKey;
    }

    public static HttpMethod from(String requestKey) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.requestKey.equals(requestKey))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 HttpMethod로 요청되었습니다. - " + requestKey));
    }
}
