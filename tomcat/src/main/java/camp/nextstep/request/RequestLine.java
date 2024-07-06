package camp.nextstep.request;

public class RequestLine {
    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final String QUERY_STRING_SEPARATOR = "\\?";
    public static final String PROTOCOL_AND_VERSION_SEPARATOR = "/";

    private final String requestLine;

    private String method;
    private String path;
    private String queryString;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public void process() {
        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);

        String method = split[0];
        String uri = split[1];
        String protocolAndVersion = split[2];

        this.method = method;
        extractPath(uri);
        extractQueryString(uri);
        extractProtocol(protocolAndVersion);
        extractVersion(protocolAndVersion);
    }

    private void extractPath(String uri) {
        this.path = uri.split(QUERY_STRING_SEPARATOR)[0];
    }

    private void extractQueryString(String uri) {
        String result = "";
        String[] split = uri.split("\\?", 2);
        if (split.length >= 2) {
            result = split[1];
        }
        this.queryString = result;
    }

    private void extractProtocol(String protocolAndVersion) {
        this.protocol = protocolAndVersion.split(PROTOCOL_AND_VERSION_SEPARATOR)[0];
    }

    private void extractVersion(String protocolAndVersion) {
        this.version = protocolAndVersion.split(PROTOCOL_AND_VERSION_SEPARATOR)[1];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getQueryString() {
        return queryString;
    }
}
