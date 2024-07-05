package camp.nextstep.domain.http;

public class HttpProtocol {

    public HttpProtocol(String httpProtocol) {
        String[] splitHttpProtocol = httpProtocol.split("/");
        if (splitHttpProtocol.length != 2) {
            throw new IllegalArgumentException("HttpProtocol값이 정상적으로 입력되지 않았습니다 - "+ httpProtocol);
        }
    }
}
