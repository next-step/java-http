package camp.nextstep.domain.http;

public class RequestLine {

    private final String httpMethod;
    private final String httpUrl;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = parseRequestLine(requestLine);
        httpMethod = parseHttpMethod(splitRequestLine);
        httpUrl = splitRequestLine[1];
        parseProtocol(splitRequestLine[2].split("/"));
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

    private String parseProtocol(String[] splitHttpProtocol) {
        String protocol = splitHttpProtocol[0];
        if (protocol.isEmpty()) {
            throw new IllegalArgumentException("protocol값이 존재하지 않습니다.");
        }
        return protocol;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getHttpUrl() {
        return httpUrl;
    }
}
