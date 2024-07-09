package camp.nextstep.http.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final RequestBody requestBody;

    public HttpRequest(final InputStream inputStream) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        this.requestLine = new RequestLine(br.readLine());
        this.headers = new HttpHeaders(extractHeaders(br));
        this.requestBody = extractRequestBody(br, headers);

    }

    private RequestBody extractRequestBody(final BufferedReader br, final HttpHeaders requestHeaders) throws IOException {
        if (requestHeaders.isContentLengthEmpty()) {
            return new RequestBody();
        }
        final int contentLength = requestHeaders.getContentLength();
        final char[] bodyChars = new char[contentLength];
        br.read(bodyChars, 0, contentLength);
        return new RequestBody(new String(bodyChars));
    }

    private static List<String> extractHeaders(final BufferedReader br) {
        return br.lines()
                .takeWhile(line -> !line.isEmpty())
                .toList();
    }

    public HttpPath getPath() {
        return requestLine.getPath();
    }

    public QueryParameters getQueryParameters() {
        return requestLine.getQueryParameters();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public boolean isGetMethod() {
        return requestLine.isGet();
    }

    public boolean isPostMethod() {
        return requestLine.isPost();
    }

    public boolean isSessionEmpty() {
        return headers.getCookie("JESSIONID") == null;
    }
}
