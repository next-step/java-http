package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidRequestLineException;

public class RequestLine {
    private static final int REQUIRED_REQUEST_LINE_LENGTH = 3;
    private static final int METHOD_INDEX = 0;
    private static final int REQUEST_URI_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final String DELIMITER = " ";

    private final HttpMethod method;
    private final RequestURI requestURI;
    private final HttpVersion version;

    public RequestLine(final String requestLine) {
        checkNull(requestLine);
        final String[] parsedRequestLine = parseRequestLine(requestLine);
        this.method = HttpMethod.valueOf(parsedRequestLine[METHOD_INDEX]);
        this.requestURI = new RequestURI(parsedRequestLine[REQUEST_URI_INDEX]);
        this.version = new HttpVersion(parsedRequestLine[VERSION_INDEX]);
    }

    private void checkNull(final String requestLine) {
        if (requestLine == null) {
            throw new InvalidRequestLineException("requestLine cannot be null");
        }
    }

    private String[] parseRequestLine(final String requestLine) {
        final String[] splitRequestLine = requestLine.split(DELIMITER);
        if (splitRequestLine.length != REQUIRED_REQUEST_LINE_LENGTH) {
            throw new InvalidRequestLineException("Invalid request line: " + requestLine);
        }
        return splitRequestLine;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public QueryParameters getQueryParameters() {
        return requestURI.getQueryParameters();
    }

    public HttpPath getPath() {
        return requestURI.getPath();
    }

    public boolean isPost() {
        return method == HttpMethod.POST;
    }

    public boolean isGet() {
        return method == HttpMethod.GET;
    }
}
