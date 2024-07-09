package camp.nextstep.domain.http;

import java.util.Map;

public class HttpPath {

    private static final String HTTP_PATH_FORMAT_SPLIT_REGEX = "\\?";
    private static final int HTTP_PATH_EMPTY_QUERY_STRING_FORMAT_LENGTH = 1;
    private static final int HTTP_PATH_EXIST_QUERY_STRING_FORMAT_LENGTH = 2;

    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;

    private final String path;
    private final QueryString queryString;

    public HttpPath(String httpPath) {
        String[] splitHttpPath = httpPath.split(HTTP_PATH_FORMAT_SPLIT_REGEX);
        validatePathFormat(httpPath, splitHttpPath);
        this.path = splitHttpPath[PATH_INDEX];
        this.queryString = parseQueryString(httpPath, splitHttpPath);
    }

    private static void validatePathFormat(String httpPath, String[] splitHttpPath) {
        if (splitHttpPath.length != HTTP_PATH_EMPTY_QUERY_STRING_FORMAT_LENGTH && splitHttpPath.length != HTTP_PATH_EXIST_QUERY_STRING_FORMAT_LENGTH) {
            throw new IllegalArgumentException("HttpPath값이 정상적으로 입력되지 않았습니다 - " + httpPath);
        }
    }

    private QueryString parseQueryString(String httpPath, String[] splitHttpPath) {
        if (splitHttpPath.length == HTTP_PATH_EXIST_QUERY_STRING_FORMAT_LENGTH) {
            return new QueryString(splitHttpPath[QUERY_STRING_INDEX]);
        }
        if (splitHttpPath.length == HTTP_PATH_EMPTY_QUERY_STRING_FORMAT_LENGTH) {
            return new QueryString();
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
