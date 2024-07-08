package camp.nextstep.model.dto;

public class RequestLine {
    String method;
    String path;
    String protocol;
    String version;

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

    public static RequestLine of(String method, String path, String protocol, String version) {
        RequestLine requestLine = new RequestLine();
        requestLine.method = method;
        requestLine.path = path;
        requestLine.protocol = protocol;
        requestLine.version = version;
        return requestLine;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
