package camp.nextstep.domain.http;

public class HttpResponse {

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatus httpStatus) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
    }

    public static HttpResponse ok(HttpProtocol httpProtocol) {
        return new HttpResponse(httpProtocol, HttpStatus.OK);
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
