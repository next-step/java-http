package camp.nextstep.domain.http;

import java.util.Map;

import static camp.nextstep.util.FileUtil.containsExtensionDelimiter;

public class RequestLine {

    private static final String REQUEST_LINE_FORMAT_SPLIT_REGEX = " ";
    private static final int REQUEST_LINE_FORMAT_LENGTH = 3;

    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_URL_INDEX = 1;
    private static final int HTTP_PROTOCOL_INDEX = 2;

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.TEXT_HTML;

    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final HttpProtocol httpProtocol;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = parseRequestLine(requestLine);
        this.httpMethod = HttpMethod.from(splitRequestLine[HTTP_METHOD_INDEX]);
        this.httpPath = new HttpPath(splitRequestLine[HTTP_URL_INDEX]);
        this.httpProtocol = new HttpProtocol(splitRequestLine[HTTP_PROTOCOL_INDEX]);
    }

    private String[] parseRequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(REQUEST_LINE_FORMAT_SPLIT_REGEX);
        if (splitRequestLine.length != REQUEST_LINE_FORMAT_LENGTH) {
            throw new IllegalArgumentException("RequestLine값이 정상적으로 입력되지 않았습니다 - " + requestLine);
        }
        return splitRequestLine;
    }

    public boolean isGetMethod() {
        return httpMethod == HttpMethod.GET;
    }

    public boolean isPostMethod() {
        return httpMethod == HttpMethod.POST;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getHttpPath() {
        return httpPath.getPath();
    }

    public String getFilePath() {
        if (containsExtensionDelimiter(httpPath.getPath())) {
            return httpPath.getPath();
        }
        return httpPath.getPath() + DEFAULT_CONTENT_TYPE.getExtension();
    }

    public Map<String, String> getQueryString() {
        return httpPath.getQueryString();
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }
}
