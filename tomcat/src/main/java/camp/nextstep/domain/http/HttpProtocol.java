package camp.nextstep.domain.http;

public class HttpProtocol {

    private static final String HTTP_PROTOCOL_FORMAT_SPLIT_REGEX = "/";
    private static final int HTTP_PROTOCOL_FORMAT_LENGTH = 2;

    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private final String protocol;
    private final String version;

    public HttpProtocol(String httpProtocol) {
        String[] splitHttpProtocol = splitHttpProtocol(httpProtocol);
        this.protocol = splitHttpProtocol[PROTOCOL_INDEX];
        this.version = splitHttpProtocol[VERSION_INDEX];
    }

    private static String[] splitHttpProtocol(String httpProtocol) {
        String[] splitHttpProtocol = httpProtocol.split(HTTP_PROTOCOL_FORMAT_SPLIT_REGEX);
        if (splitHttpProtocol.length != HTTP_PROTOCOL_FORMAT_LENGTH) {
            throw new IllegalArgumentException("HttpProtocol값이 정상적으로 입력되지 않았습니다 - "+ httpProtocol);
        }
        return splitHttpProtocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
