package org.apache.coyote;

import org.apache.http.HttpProtocol;
import org.apache.http.HttpStatus;

public class HttpResponseStatusLine {
    private static final String DELIMITER = " ";

    private final HttpProtocol protocol;
    private final HttpStatus status;

    public HttpResponseStatusLine(HttpStatus status) {
        this.protocol = HttpProtocol.HTTP_11;
        this.status = status;
    }

    @Override
    public String toString() {
        return protocol + DELIMITER + status + DELIMITER;
    }
}
