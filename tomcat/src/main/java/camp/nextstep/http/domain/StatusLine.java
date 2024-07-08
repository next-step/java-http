package camp.nextstep.http.domain;

public class StatusLine {
    private static final String DELIMITER = " ";

    private final HttpVersion version;


    public StatusLine(final String statusLine) {
        final String[] parsedStatusLine = statusLine.split(DELIMITER);
        this.version = new HttpVersion(parsedStatusLine[0]);
    }

    public HttpVersion getVersion() {
        return version;
    }
}
