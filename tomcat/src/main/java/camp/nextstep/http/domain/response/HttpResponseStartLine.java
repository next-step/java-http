package camp.nextstep.http.domain.response;

public class HttpResponseStartLine {
    private static final String SUCCESS_RESPONSE_START_LINE = "HTTP/1.1 200 OK ";
    private static final String NOTFOUND_RESPONSE_START_LINE = "HTTP/1.1 404 NOT FOUND ";
    private static final String REDIRECT_RESPONSE_START_LINE = "HTTP/1.1 302 FOUND ";
    private static final String BAD_REQUEST_RESPONSE_START_LINE = "HTTP/1.1 401 BAD REQUEST ";

    private String responseStartLine;

    private HttpResponseStartLine(String responseStartLine) {
        this.responseStartLine = responseStartLine;
    }

    public static HttpResponseStartLine createSuccessStartLine() {
        return new HttpResponseStartLine(SUCCESS_RESPONSE_START_LINE);
    }

    public static HttpResponseStartLine createNotFoundStartLine() {
        return new HttpResponseStartLine(NOTFOUND_RESPONSE_START_LINE);
    }

    public static HttpResponseStartLine createRedirectStartLine() {
        return new HttpResponseStartLine(REDIRECT_RESPONSE_START_LINE);
    }

    public static HttpResponseStartLine createBadRequestStartLine() {
        return new HttpResponseStartLine(BAD_REQUEST_RESPONSE_START_LINE);
    }

    public String getResponseStartLine() {
        return responseStartLine;
    }
}
