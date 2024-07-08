package camp.nextstep.http.domain;

import org.apache.coyote.http11.HttpWritable;

public class StatusLine implements HttpWritable {
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

    @Override
    public byte[] getBytes() {
        return String.format(
                "%s/%s %d %s",
                version.getProtocol(),
                version.getVersion(),
                statusCode.getValue(),
                statusCode.getReasonPhrase()
        ).getBytes();
    }
}
