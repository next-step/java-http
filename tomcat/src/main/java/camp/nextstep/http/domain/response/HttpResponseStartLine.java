package camp.nextstep.http.domain.response;

import camp.nextstep.enums.Protocol;
import camp.nextstep.http.enums.HttpStatus;
import camp.nextstep.http.enums.HttpVersion;

public class HttpResponseStartLine {
    private static final String HTTP_RESPONSE_START_LINE_FORMAT = "%s/%s %s %s ";

    private Protocol protocol;
    private HttpVersion version;
    private HttpStatus httpStatus;

    public HttpResponseStartLine(Protocol protocol, HttpVersion version, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.version = version;
        this.httpStatus = httpStatus;
    }

    public static HttpResponseStartLine createSuccessStartLine() {
        return new HttpResponseStartLine(
                Protocol.HTTP,
                HttpVersion.VERSION_1_1,
                HttpStatus.OK
        );
    }

    public static HttpResponseStartLine createNotFoundStartLine() {
        return new HttpResponseStartLine(
                Protocol.HTTP,
                HttpVersion.VERSION_1_1,
                HttpStatus.NOT_FOUND
        );
    }

    public static HttpResponseStartLine createRedirectStartLine() {
        return new HttpResponseStartLine(
                Protocol.HTTP,
                HttpVersion.VERSION_1_1,
                HttpStatus.FOUND
        );
    }

    public static HttpResponseStartLine createBadRequestStartLine() {
        return new HttpResponseStartLine(
                Protocol.HTTP,
                HttpVersion.VERSION_1_1,
                HttpStatus.BAD_REQUEST
        );
    }

    public String getResponseStartLine() {
        return String.format(
                HTTP_RESPONSE_START_LINE_FORMAT,
                protocol.name(),
                version.name(),
                httpStatus.getStatusCode(),
                httpStatus.getMessage()
        );
    }
}
