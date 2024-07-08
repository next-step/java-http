package camp.nextstep.http.domain;

public class StatusLine {
    private static final int REQUIRED_STATUS_LINE_LENGTH = 3;
    private static final String DELIMITER = " ";

    private final HttpVersion version;
    private final int statusCode;
    private final String reasonPhase;

    public StatusLine(final String statusLine) {
        final String[] parsedStatusLine = statusLine.split(DELIMITER);
        this.version = new HttpVersion(parsedStatusLine[0]);
        this.statusCode = Integer.parseInt(parsedStatusLine[1]);
        this.reasonPhase = parsedStatusLine[2];
    }

    public HttpVersion getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhase() {
        return reasonPhase;
    }
}
