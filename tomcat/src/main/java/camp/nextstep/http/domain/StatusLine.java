package camp.nextstep.http.domain;

public class StatusLine {
    private static final String DELIMITER = " ";

    private final HttpVersion version;
    private final int statusCode;

    public StatusLine(final String statusLine) {
        final String[] parsedStatusLine = statusLine.split(DELIMITER);
        this.version = new HttpVersion(parsedStatusLine[0]);
        this.statusCode = Integer.parseInt(parsedStatusLine[1]);
    }

    public HttpVersion getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
