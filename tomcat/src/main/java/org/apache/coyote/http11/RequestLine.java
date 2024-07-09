package org.apache.coyote.http11;


import java.util.Map;

public class RequestLine {

    private final static String DELIMITER = " ";
    private final static int REQUEST_LINE_CHUNK_LIMIT = 3;

    private HttpMethod method;
    private Path path;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {
        String[] infos = split(requestLine);
        if (infos.length != REQUEST_LINE_CHUNK_LIMIT) {
            throw new IllegalArgumentException("RequestLine is invalid: " + requestLine);
        }
        this.method = HttpMethod.from(infos[0]);
        this.path = new Path(infos[1]);

        var protocols = infos[2].split("/");
        this.protocol = protocols[0];
        this.version = protocols[1];
    }

    public boolean pathEndsWith(String value) {
        return path.endsWith(value);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public Map<String, Object> getQueryParamMap() {
        return path.getQueryParamMap();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }


    private static String[] split(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalArgumentException("RequestLine is null or empty: " + requestLine);
        }
        return requestLine.split(DELIMITER);
    }
}
