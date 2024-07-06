package camp.nextstep.io;

public class RequestLine {
    private String method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String request) {
        String[] tokens = request.split(" ");
        this.method = tokens[0];
        this.path = tokens[1];
        String[] protocolAndVersion = tokens[2].split("/");
        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];
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
