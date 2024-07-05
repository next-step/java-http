package camp.nextstep.domain.http;

public class RequestLine {

    private static final String REQUEST_LINE_FORMAT_SPLIT_REGEX = " ";
    private static final int REQUEST_LINE_FORMAT_LENGTH = 3;

    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_URL_INDEX = 1;
    private static final int HTTP_PROTOCOL_INDEX = 2;

    private final String httpMethod;
    private final String httpUrl;
    private final HttpProtocol httpProtocol;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = parseRequestLine(requestLine);
        this.httpMethod = parseHttpMethod(splitRequestLine);
        this.httpUrl = splitRequestLine[HTTP_URL_INDEX];
        this.httpProtocol = new HttpProtocol(splitRequestLine[HTTP_PROTOCOL_INDEX]);
    }

    private String[] parseRequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(REQUEST_LINE_FORMAT_SPLIT_REGEX);
        if (splitRequestLine.length != REQUEST_LINE_FORMAT_LENGTH) {
            throw new IllegalArgumentException("RequestLine값이 정상적으로 입력되지 않았습니다 - " + requestLine);
        }
        return splitRequestLine;
    }

    private String parseHttpMethod(String[] splitRequestLine) {
        String httpMethod = splitRequestLine[HTTP_METHOD_INDEX];
        if (!httpMethod.equals("GET")) {
            throw new IllegalArgumentException("HttpStatus값이 존재하지 않습니다.");
        }
        return httpMethod;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public String getProtocol() {
        return httpProtocol.getProtocol();
    }

    public String getProtocolVersion() {
        return httpProtocol.getVersion();
    }
}
