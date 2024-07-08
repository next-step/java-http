package camp.nextstep.domain.http;

import java.util.Map;

public class HttpResponse {

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> httpHeaders;
    private final String responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatus httpStatus, String responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
        this.httpHeaders = Map.of("Content-Length", String.valueOf(responseBody.getBytes().length));
        this.responseBody = responseBody;
    }

    public static HttpResponse ok(HttpProtocol httpProtocol, String responseBody) {
        return new HttpResponse(httpProtocol, HttpStatus.OK, responseBody);
    }

    public static HttpResponse found(HttpProtocol httpProtocol, String responseBody) {
        return new HttpResponse(httpProtocol, HttpStatus.FOUND, responseBody);
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
