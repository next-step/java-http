package camp.nextstep.request;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final QueryParameters requestBody;

    public Request(RequestLine requestLine, RequestHeaders requestHeaders, QueryParameters requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public boolean isGET() {
        return requestLine.getMethod() == RequestMethod.GET;
    }

    public boolean isPOST() {
        return requestLine.getMethod() == RequestMethod.POST;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public QueryParameters getRequestBody() {
        return requestBody;
    }
}
