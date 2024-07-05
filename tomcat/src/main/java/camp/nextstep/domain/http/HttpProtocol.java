package camp.nextstep.domain.http;

public class HttpProtocol {

    private final String protocol;
    private final String version;

    public HttpProtocol(String httpProtocol) {
        String[] splitHttpProtocol = httpProtocol.split("/");
        if (splitHttpProtocol.length != 2) {
            throw new IllegalArgumentException("HttpProtocol값이 정상적으로 입력되지 않았습니다 - "+ httpProtocol);
        }
        this.protocol = splitHttpProtocol[0];
        this.version = splitHttpProtocol[1];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
