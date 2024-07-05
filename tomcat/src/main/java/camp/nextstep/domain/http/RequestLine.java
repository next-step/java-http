package camp.nextstep.domain.http;

public class RequestLine {

    private final String httpMethod;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        if (!splitRequestLine[0].equals("GET")) {
            throw new IllegalArgumentException("HttpStatus값이 존재하지 않습니다.");
        }
        httpMethod = splitRequestLine[0];
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}
