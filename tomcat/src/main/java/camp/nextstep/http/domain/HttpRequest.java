package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class HttpRequest {

    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final int SPLIT_LIMIT = 2;

    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final RequestBody requestBody;

    public HttpRequest(final InputStream inputStream) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        this.requestLine = new RequestLine(br.readLine());
        this.headers = readHeader(br);
        this.requestBody = readRequestBody(br, headers);
    }

    private HttpHeaders readHeader(final BufferedReader br) {
        return new HttpHeaders(br.lines()
                .takeWhile(line -> !line.isEmpty())
                .map(this::splitHeader)
                .collect(Collectors.toMap(
                        header -> header[HEADER_KEY_INDEX].trim(),
                        header -> header[HEADER_VALUE_INDEX].trim(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                )));
    }

    private String[] splitHeader(final String headerLine) {
        if (headerLine.contains(HttpHeaders.DELIMITER)) {
            return headerLine.split(HttpHeaders.DELIMITER, SPLIT_LIMIT);
        }
        throw new InvalidHttpHeaderException("Invalid HTTP header line: " + headerLine);
    }

    private RequestBody readRequestBody(final BufferedReader br, final HttpHeaders requestHeaders) throws IOException {
        if (requestHeaders.isContentLengthEmpty()) {
            return new RequestBody();
        }
        final int contentLength = requestHeaders.getContentLength();
        final char[] bodyChars = new char[contentLength];
        br.read(bodyChars, 0, contentLength);
        return new RequestBody(new String(bodyChars));
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
        return headers.getSessionCookie() == null;
    }

    public HttpSession getSession() {
        final HttpSession session = HttpSessionManager.computeIfAbsent(headers.getSessionCookie());
        headers.addSessionCookie(session.getId());
        return session;
    }
}
