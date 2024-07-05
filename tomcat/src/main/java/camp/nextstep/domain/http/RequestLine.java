package camp.nextstep.domain.http;

public class RequestLine {

    private final String httpMethod;
    private final String httpUrl;
    private final String protocol;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = parseRequestLine(requestLine);
        httpMethod = parseHttpMethod(splitRequestLine);
        httpUrl = splitRequestLine[1];
        String[] httpProtocol = parseHttpProtocol(splitRequestLine);
        this.protocol = httpProtocol[0];
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

    private String[] parseHttpProtocol(String[] splitRequestLine) {
        String[] httpProtocol = splitRequestLine[2].split("/");
        if (httpProtocol.length != 2) {
            throw new IllegalArgumentException("HttpProtocol값이 정상적으로 입력되지 않았습니다 - "+ splitRequestLine[2]);
        }
        return httpProtocol;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public String getProtocol() {
        return protocol;
    }
}
