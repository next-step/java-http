package camp.nextstep.request;

public class RequestLine {
    private final RequestMethod method;
    private final String path;
    private final QueryParameters queryParameters;
    private final String httpVersion;

    public RequestLine(RequestMethod method, String path, QueryParameters queryParameters, String httpVersion) {
        this.method = method;
        this.path = path;
        this.queryParameters = queryParameters;
        this.httpVersion = httpVersion;
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
