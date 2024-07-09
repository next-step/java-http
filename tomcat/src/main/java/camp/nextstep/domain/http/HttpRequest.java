package camp.nextstep.domain.http;

import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, HttpRequestBody httpRequestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.httpRequestBody = httpRequestBody;
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getHttpPath() {
        return requestLine.getHttpPath();
    }

    public String getFilePath() {
        return requestLine.getFilePath();
    }

    public HttpProtocol getHttpProtocol() {
        return requestLine.getHttpProtocol();
    }

    public Map<String, String> getQueryString() {
        return requestLine.getQueryString();
    }

    public Map<String, String> getHttpRequestBody() {
        return httpRequestBody.getValues();
    }
}
