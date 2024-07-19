package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpRequestSpecException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static camp.nextstep.http.domain.HttpHeader.createHeadersFromReader;
import static camp.nextstep.http.domain.HttpRequestBody.createHttpRequestBodyFromReader;
import static camp.nextstep.http.domain.HttpStartLine.createHttpStartLineByReader;

public class RequestLine {
    private HttpStartLine httpStartLine;
    private HttpHeader httpHeader;
    private HttpRequestBody httpRequestBody;

    private RequestLine(HttpStartLine httpStartLine, HttpHeader httpHeader) {
        this.httpStartLine = httpStartLine;
        this.httpHeader = httpHeader;
    }

    private RequestLine(HttpStartLine httpStartLine, HttpHeader httpHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpHeader = httpHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpStartLine getHttpStartLine() {
        return httpStartLine;
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    public static RequestLine createRequestLineByInputStream(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            HttpStartLine httpStartLine = createHttpStartLineByReader(bufferedReader);
            HttpHeader httpHeader = createHeadersFromReader(bufferedReader);
            if (httpHeader.hasContentLength()) {
                HttpRequestBody httpRequestBody = createHttpRequestBodyFromReader(
                        bufferedReader,
                        httpHeader.getContentType(),
                        httpHeader.getContentLength()
                );
                return new RequestLine(httpStartLine, httpHeader, httpRequestBody);
            }
            return new RequestLine(httpStartLine, httpHeader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidHttpRequestSpecException("지원하는 메소드가 아닙니다");
        }
    }
}
