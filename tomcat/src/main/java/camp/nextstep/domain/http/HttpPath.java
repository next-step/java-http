package camp.nextstep.domain.http;

public class HttpPath {

    private final String path;

    public HttpPath(String httpPath) {
        String[] splitHttpPath = httpPath.split("\\?");
        if (splitHttpPath.length > 2) {
            throw new IllegalArgumentException("HttpPath값이 정상적으로 입력되지 않았습니다 - " + httpPath);
        }
        this.path = splitHttpPath[0];
    }

    public String getPath() {
        return path;
    }
}
