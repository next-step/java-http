package camp.nextstep.request;

public class HttpRequestLine {
    private static final String REQUEST_LINE_REGEX_SEPARATOR = " ";
    private static final String QUERY_STRING_REGEX_SEPARATOR = "\\?";

    public static HttpRequestLine parse(String requestLineString) {
        final String[] split = requestLineString.split(REQUEST_LINE_REGEX_SEPARATOR, 3);

        final HttpRequestMethod method = HttpRequestMethod.valueOf(split[0]);
        final String path = split[1].split(QUERY_STRING_REGEX_SEPARATOR)[0];
        final String queryString = extractQueryStringFromUri(split[1]);
        final HttpQueryParameters queryParameters = HttpQueryParameters.parse(queryString);
        final String httpVersion = split[2];

        return new HttpRequestLine(method, path, queryParameters, httpVersion);
    }

    private static String extractQueryStringFromUri(String path) {
        final String[] split = path.split(QUERY_STRING_REGEX_SEPARATOR, 2);
        if (split.length != 2) return null;

        return split[1];
    }

    private final HttpRequestMethod method;
    private final String path;
    private final HttpQueryParameters queryParameters;
    private final String httpVersion;

    private HttpRequestLine(HttpRequestMethod method, String path, HttpQueryParameters queryParameters, String httpVersion) {
        this.method = method;
        this.path = path;
        this.queryParameters = queryParameters;
        this.httpVersion = httpVersion;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HttpQueryParameters getQueryParameters() {
        return queryParameters;
    }

    public Object getQueryParameter(String key) {
        return getQueryParameters().get(key);
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
