package camp.nextstep.domain.http;

import java.util.Map;

public class HttpPath {

    private final String path;
    private final QueryString queryString;

    public HttpPath(String httpPath) {
        String[] splitHttpPath = httpPath.split("\\?");
        this.path = splitHttpPath[0];
        this.queryString = parseQueryString(httpPath, splitHttpPath);
    }

    private QueryString parseQueryString(String httpPath, String[] splitHttpPath) {
        if (splitHttpPath.length == 2) {
            return new QueryString(splitHttpPath[1]);
        }
        if (splitHttpPath.length == 1) {
            return QueryString.empty();
        }
        throw new IllegalArgumentException("HttpPath값이 정상적으로 입력되지 않았습니다 - " + httpPath);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryString() {
        return queryString.getQueryParameters();
    }
}
