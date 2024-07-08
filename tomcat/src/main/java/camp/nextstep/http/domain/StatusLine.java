package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpStatusException;
import camp.nextstep.http.exception.InvalidStatusLineException;

public class StatusLine {
    private static final int REQUIRED_STATUS_LINE_LENGTH = 3;
    private static final String DELIMITER = " ";

    private final HttpVersion version;
    private final HttpStatusCode statusCode;

    public StatusLine(final String statusLine) {
        checkNull(statusLine);
        final String[] parsedStatusLine = parseStatusLine(statusLine);
        this.version = new HttpVersion(parsedStatusLine[0]);
        this.statusCode = convertStatusCode(parsedStatusLine[1]);
    }

    private void checkNull(final String statusLine) {
        if (statusLine == null) {
            throw new InvalidStatusLineException("statusLine cannot be null");
        }
    }

    private String[] parseStatusLine(final String statusLine) {
        final String[] splitRequestLine = statusLine.split(DELIMITER);
        if (splitRequestLine.length != REQUIRED_STATUS_LINE_LENGTH) {
            throw new InvalidStatusLineException("Invalid request line: " + statusLine);
        }
        return splitRequestLine;
    }

    private HttpStatusCode convertStatusCode(final String statusCode) {
        try {
            return HttpStatusCode.valueOf(Integer.parseInt(statusCode));
        } catch (final NumberFormatException | InvalidHttpStatusException e) {
            throw new InvalidStatusLineException(statusCode, e);
        }
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
