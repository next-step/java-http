package camp.nextstep.request;

public class RequestLine {
    private final String requestLine;

    private String method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public void process() {
        String[] split = requestLine.split(" ");

        String method = split[0];
        String uri = split[1];
        String protocolAndVersion = split[2];

        this.method = method;
        this.path = uri;
        this.protocol = protocolAndVersion.split("/")[0];
        this.version = protocolAndVersion.split("/")[1];
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
