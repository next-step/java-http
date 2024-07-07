package camp.nextstep.http.domain;

public class RequestLine {

    private final String method;
    private final String path;

    public RequestLine(final String requestLine) {
        this.method = requestLine.split(" ")[0];
        this.path = requestLine.split(" ")[1];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
