package camp.nextstep.http.domain;

public class RequestLine {

    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    public RequestLine(final String requestLine) {
        this.method = requestLine.split(" ")[0];
        this.path = requestLine.split(" ")[1];
        this.protocol = requestLine.split(" ")[2].split("/")[0];
        this.version = requestLine.split(" ")[2].split("/")[1];
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
}