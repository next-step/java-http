package camp.nextstep.domain.http;

public class RequestLine {

    private final String httpMethod;

    public RequestLine(String requestLine) {
        if (!requestLine.equals("GET")) {
            throw new IllegalArgumentException("HttpStatus값이 존재하지 않습니다.");
        }
        httpMethod = requestLine;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}
