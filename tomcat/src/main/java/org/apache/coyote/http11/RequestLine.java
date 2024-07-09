package org.apache.coyote.http11;


public class RequestLine {

    private final static String DELIMITER = " ";

    private String method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {
        String[] infos = split(requestLine);
        if (infos.length != 3) {
            throw new IllegalArgumentException("RequestLine is invalid: " + requestLine);
        }
        this.method = infos[0];
        this.path = infos[1];
        var protocols = infos[2].split("/");
        this.protocol = protocols[0];
        this.version = protocols[1];
    }

    public boolean pathEndsWith(String value) {
        return path.endsWith(value);
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

    private static String[] split(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalArgumentException("RequestLine is null or empty: " + requestLine);
        }
        return requestLine.split(DELIMITER);
    }
}
