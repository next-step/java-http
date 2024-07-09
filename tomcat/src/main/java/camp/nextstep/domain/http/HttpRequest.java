package camp.nextstep.domain.http;

import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeaders httpRequestHeaders;
    private final HttpRequestBody requestBody;

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpRequestHeaders = httpRequestHeaders;
        this.requestBody = requestBody;
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
}
