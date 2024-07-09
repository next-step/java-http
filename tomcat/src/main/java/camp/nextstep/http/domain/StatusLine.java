package camp.nextstep.http.domain;

public class StatusLine {
    private final HttpVersion version;
    private final HttpStatusCode statusCode;

    private StatusLine(final HttpVersion httpVersion, final HttpStatusCode statusCode) {
        this.version = httpVersion;
        this.statusCode = statusCode;
    }

    public static StatusLine createOk() {
        return new StatusLine(HttpVersion.HTTP_11, HttpStatusCode.OK);
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public String convertToString() {
        return String.format(
                "%s/%s %d %s ",
                version.getProtocol(),
                version.getVersion(),
                statusCode.getValue(),
                statusCode.getReasonPhrase()
        );
    }
}
