package camp.nextstep.domain.http.request;

import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.HttpHeaders;
import camp.nextstep.domain.http.HttpProtocol;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final HttpCookie httpCookie;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, HttpCookie httpCookie, HttpRequestBody httpRequestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.httpCookie = httpCookie;
        this.httpRequestBody = httpRequestBody;
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public boolean containsSessionId() {
        return httpCookie.containsSessionId();
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

    public String getSessionId() {
        return httpCookie.getSessionId();
    }

    public String getHttpRequestBodyValue(String key) {
        return httpRequestBody.getValue(key);
    }
}
