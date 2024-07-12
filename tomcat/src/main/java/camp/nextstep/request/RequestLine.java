package camp.nextstep.request;

public class RequestLine {
    private static final String REQUEST_LINE_REGEX_SEPARATOR = " ";
    private static final String QUERY_STRING_REGEX_SEPARATOR = "\\?";
    private final RequestMethod method;
    private final String path;
    private final QueryParameters queryParameters;
    private final String httpVersion;

    private RequestLine(RequestMethod method, String path, QueryParameters queryParameters, String httpVersion) {
        this.method = method;
        this.path = path;
        this.queryParameters = queryParameters;
        this.httpVersion = httpVersion;
    }

    public static RequestLine parse(String requestLineString) {
        final String[] split = requestLineString.split(REQUEST_LINE_REGEX_SEPARATOR, 3);

        final RequestMethod method = RequestMethod.valueOf(split[0]);
        final String path = split[1].split(QUERY_STRING_REGEX_SEPARATOR)[0];
        final String queryString = extractQueryStringFromUri(split[1]);
        final QueryParameters queryParameters = QueryParameters.parse(queryString);
        final String httpVersion = split[2];

        return new RequestLine(method, path, queryParameters, httpVersion);
    }

    private static String extractQueryStringFromUri(String path) {
        final String[] split = path.split(QUERY_STRING_REGEX_SEPARATOR, 2);
        if (split.length != 2) return null;

        return split[1];
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

    public Object getQueryParameter(String key) {
        return getQueryParameters().get(key);
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
