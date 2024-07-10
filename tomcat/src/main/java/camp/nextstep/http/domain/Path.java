package camp.nextstep.http.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Path {
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAM_INDEX = 1;

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
        String[] pathStrs = path.split("\\?");
        if (pathStrs.length == 2) {
            Map<String, String> queryParams = getQueryParams(pathStrs[QUERY_PARAM_INDEX]);
            return new Path(pathStrs[PATH_INDEX], queryParams);
        }

        return new Path(pathStrs[PATH_INDEX]);
    }

    private static Map<String, String> getQueryParams(String queryParamStr) {
        String[] queryParamArgs = queryParamStr.split("&");
        return Arrays.stream(queryParamArgs)
                .map(v -> v.split("="))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }
}
