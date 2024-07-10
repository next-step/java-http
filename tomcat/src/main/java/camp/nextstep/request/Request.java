package camp.nextstep.request;

public class Request {
    private final RequestLine requestLine;

    public Request(RequestMethod method, String path, QueryParameters queryParameters, String httpVersion) {
        this.requestLine = new RequestLine(method, path, queryParameters, httpVersion);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
