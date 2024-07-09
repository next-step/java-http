package org.apache.coyote;

import org.apache.http.HttpProtocol;
import org.apache.http.HttpStatus;

public class HttpResponseStatusLine {
    private static final String DELIMITER = " ";
    public static final HttpResponseStatusLine HTTP_11_OK = new HttpResponseStatusLine(HttpProtocol.HTTP_11, HttpStatus.OK);

    private final HttpProtocol protocol;
    private final HttpStatus status;

    public HttpResponseStatusLine(HttpProtocol protocol, HttpStatus status) {
        this.protocol = protocol;
        this.status = status;
    }

    @Override
    public String toString() {
        return protocol + DELIMITER + status + DELIMITER;
    }
}
