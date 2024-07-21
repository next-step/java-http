package camp.nextstep.http.domain.request;

import camp.nextstep.http.exception.InvalidHttpRequestSpecException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static camp.nextstep.http.domain.request.HttpHeader.createHeadersFromReader;
import static camp.nextstep.http.domain.request.HttpRequestBody.createHttpRequestBodyFromReader;
import static camp.nextstep.http.domain.request.HttpRequestStartLine.createHttpStartLineByReader;

public class HttpRequest {
    private HttpRequestStartLine httpStartLine;
    private HttpHeader httpHeader;
    private HttpRequestBody httpRequestBody;

    private HttpRequest(HttpRequestStartLine httpStartLine, HttpHeader httpHeader) {
        this.httpStartLine = httpStartLine;
        this.httpHeader = httpHeader;
    }

    private HttpRequest(HttpRequestStartLine httpStartLine, HttpHeader httpHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpHeader = httpHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequestStartLine getHttpStartLine() {
        return httpStartLine;
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public static HttpRequest createRequestLineByInputStream(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            HttpRequestStartLine httpStartLine = createHttpStartLineByReader(bufferedReader);
            HttpHeader httpHeader = createHeadersFromReader(bufferedReader);
            if (httpHeader.hasContentLength()) {
                HttpRequestBody httpRequestBody = createHttpRequestBodyFromReader(
                        bufferedReader,
                        httpHeader.getContentType(),
                        httpHeader.getContentLength()
                );
                return new HttpRequest(httpStartLine, httpHeader, httpRequestBody);
            }
            return new HttpRequest(httpStartLine, httpHeader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidHttpRequestSpecException("지원하는 메소드가 아닙니다");
        }
    }
}
