package camp.nextstep.request;

public class Request {
    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final String QUERY_STRING_SEPARATOR = "\\?";
    public static final String PROTOCOL_AND_VERSION_SEPARATOR = "/";

    private final RequestMethod method;
    private final String path;
    private final String queryString;
    private final String protocol;
    private final String version;

    public Request(String requestLine) {
        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);

        String method = split[0];
        String uri = split[1];
        String protocolAndVersion = split[2];

        this.method = RequestMethod.valueOf(method);

        // XXX: 아래 두개 묶기 (uri)
        this.path = uri.split(QUERY_STRING_SEPARATOR)[0];
        this.queryString = getString(uri);

        this.protocol = protocolAndVersion.split(PROTOCOL_AND_VERSION_SEPARATOR)[0];
        this.version = protocolAndVersion.split(PROTOCOL_AND_VERSION_SEPARATOR)[1];
    }

    // XXX: 얘 머지?
    private static String getString(String uri) {
        String result = "";
        String[] split = uri.split("\\?", 2);
        if (split.length >= 2) {
            result = split[1];
        }
        return result;
    }

    public RequestMethod getMethod() {
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

    // XXX 얘도 로직 떼내기. MimeTypes
    public String getPredictedMimeType() {
        if (path.equals("/")) {
            return "text/html";
        }

        if (path.endsWith(".html")) {
            return "text/html";
        }

        if (path.endsWith(".js")) {
            return "application/javascript";
        }

        if (path.endsWith(".css")) {
            return "text/css";
        }

        return "text/plain";
    }
}
