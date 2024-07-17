package camp.nextstep.http.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Path {
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAM_INDEX = 1;
    private static final int PATH_CONTAINS_QUERY_STRING_SIZE = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final Pattern QUERY_SEPARATOR = Pattern.compile("\\?");
    private static final Pattern QUERY_PARAM_SEPARATOR = Pattern.compile("&");
    private static final Pattern KEY_VALUE_SEPARATOR = Pattern.compile("=");

    private String urlPath;
    private Map<String, String> queryParams;

    private Path(String urlPath, Map<String, String> queryParams) {
        this.urlPath = urlPath;
        this.queryParams = queryParams;
    }

    private Path(String urlPath) {
        this.urlPath = urlPath;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public static Path createPathByPathStr(String path) {
        String[] pathStrs = QUERY_SEPARATOR.split(path);
        if (pathStrs.length == PATH_CONTAINS_QUERY_STRING_SIZE) {
            Map<String, String> queryParams = getQueryParams(pathStrs[QUERY_PARAM_INDEX]);
            return new Path(pathStrs[PATH_INDEX], queryParams);
        }

        return new Path(pathStrs[PATH_INDEX]);
    }

    private static Map<String, String> getQueryParams(String queryParamStr) {
        String[] queryParamArgs = QUERY_PARAM_SEPARATOR.split(queryParamStr);
        return Arrays.stream(queryParamArgs)
                .map(KEY_VALUE_SEPARATOR::split)
                .collect(Collectors.toMap(e -> e[KEY_INDEX], e -> e[VALUE_INDEX]));
    }
}
