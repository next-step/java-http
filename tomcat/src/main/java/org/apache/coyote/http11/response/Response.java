package org.apache.coyote.http11.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.http11.meta.HttpCookie;
import org.apache.coyote.http11.meta.HttpHeader;
import org.apache.coyote.http11.meta.HttpStatus;

public class Response {

    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String LOCATION_HEADER_KEY = "Location";
    private static final String HEADER_FORMAT = "%s: %s ";
    private static final String RESPONSE_STATUS_LINE_FORMAT = "%s %d %s ";

    private static final String DELIMITER = "\r\n";
    private static final String HTTP11_VERSION = "HTTP/1.1";
    private HttpStatus status;
    private String body;
    private HttpCookie cookie;
    private HttpHeader header;

    public Response() {
    }

    public Response(HttpStatus status, HttpCookie cookie, String body, Map<String, String> header) {
        this.status = status;
        this.body = body;
        this.cookie = cookie;
        this.header = new HttpHeader(header);
    }

    public void ok(ContentType contentType, String body) {
        setBasicResponse(HttpStatus.OK, contentType, body, HttpCookie.from());
    }

    public void notFound(ContentType contentType, String body) {
        setBasicResponse(HttpStatus.NOT_FOUND, contentType, body, HttpCookie.from());
    }

    public void found(HttpCookie cookie, String location) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(LOCATION_HEADER_KEY, location);
        setBasicResponse(HttpStatus.FOUND, StringUtils.EMPTY, cookie, headers);
    }

    public void found(String location) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(LOCATION_HEADER_KEY, location);
        setBasicResponse(HttpStatus.FOUND, StringUtils.EMPTY, HttpCookie.from(), headers);
    }

    public void unauthorized(String body) {
        setBasicResponse(HttpStatus.UNAUTHORIZED, ContentType.HTML, body);

    }

    public void notAllowed() {
        String body = HttpStatus.METHOD_NOT_ALLOWED.getMessage();
        setBasicResponse(HttpStatus.METHOD_NOT_ALLOWED, ContentType.HTML, body);
    }

    public byte[] toHttp11() {
        String statusLine = String.format(RESPONSE_STATUS_LINE_FORMAT, HTTP11_VERSION, status.getCode(), status.getMessage());
        String header = generateHeader();
        return String.join(DELIMITER,
            statusLine,
            header,
            "",
            body).getBytes();
    }

    private void setBasicResponse(HttpStatus status, String body, HttpCookie cookie, Map<String, String> headers) {
        this.header = new HttpHeader(headers);
        this.status = status;
        this.body = body;
        this.cookie = cookie;
    }

    private void setBasicResponse(HttpStatus status, ContentType contentType, String body, HttpCookie cookie) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(CONTENT_TYPE_HEADER_KEY, contentType.getValue());
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.getBytes().length));
        setBasicResponse(status, body, cookie, headers);
    }

    private void setBasicResponse(HttpStatus status, ContentType contentType, String body) {
        setBasicResponse(status, contentType, body, HttpCookie.from());
    }


    private String generateHeader() {
        return Stream.of(header.getHeaders(), Map.of(COOKIE_HEADER_KEY, cookie.toCookieHeader()))
            .flatMap(map -> map.entrySet().stream())
            .filter(header -> !header.getValue().isEmpty())
            .map(header -> String.format(HEADER_FORMAT, header.getKey(), header.getValue()))
            .collect(Collectors.joining(DELIMITER));
    }
}
