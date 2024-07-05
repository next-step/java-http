package camp.nextstep.domain.http;

public class RequestLine {

    private final String httpMethod;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        httpMethod = parseHttpMethod(splitRequestLine);
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
}
