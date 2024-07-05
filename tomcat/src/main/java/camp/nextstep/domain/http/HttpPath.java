package camp.nextstep.domain.http;

import java.util.Map;

public class HttpPath {

    private final String path;
    private final Map<String, String> queryString;

    public HttpPath(String httpPath) {
        String[] splitHttpPath = httpPath.split("\\?");
        this.path = splitHttpPath[0];
        if (splitHttpPath.length == 2) {
            String[] queryString = splitHttpPath[1].split("=");
            this.queryString = Map.of(queryString[0], queryString[1]);
        }
        else if (splitHttpPath.length == 1) {
            this.queryString = Map.of();
        }
        else {
            throw new IllegalArgumentException("HttpPath값이 정상적으로 입력되지 않았습니다 - " + httpPath);
        }
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }
}
