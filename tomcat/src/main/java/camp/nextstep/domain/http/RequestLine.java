package camp.nextstep.domain.http;

public class RequestLine {

    private final String httpMethod;
    private final String httpUrl;
    private final HttpProtocol httpProtocol;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = parseRequestLine(requestLine);
        this.httpMethod = parseHttpMethod(splitRequestLine);
        this.httpUrl = splitRequestLine[1];
        this.httpProtocol = new HttpProtocol(splitRequestLine[2]);
    }

    private String[] parseRequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        if (splitRequestLine.length != 3) {
            throw new IllegalArgumentException("RequestLine값이 정상적으로 입력되지 않았습니다 - " + requestLine);
        }
        return splitRequestLine;
    }

    private String parseHttpMethod(String[] splitRequestLine) {
        String httpMethod = splitRequestLine[0];
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
