package camp.nextstep.domain.http;

public class RequestLine {

    public RequestLine(String requestLine) {
        if (!requestLine.equals("GET")) {
            throw new IllegalArgumentException("HttpStatus값이 존재하지 않습니다.");
        }
    }
}
