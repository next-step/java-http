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
    private final HttpStatus status;
    private final String body;
    private final HttpCookie cookie;
    private final HttpHeader header;

    public Response(HttpStatus status, HttpCookie cookie, String body, Map<String, String> header) {
        this.status = status;
        this.body = body;
        this.cookie = cookie;
        this.header = new HttpHeader(header);
    }

    public static Response ok(ContentType contentType, String body) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(CONTENT_TYPE_HEADER_KEY, contentType.getValue());
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.getBytes().length));
        return new Response(HttpStatus.OK, HttpCookie.from(), body, headers);
    }

    public static Response notFound(ContentType contentType, String body) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(CONTENT_TYPE_HEADER_KEY, contentType.getValue());
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.getBytes().length));
        return new Response(HttpStatus.NOT_FOUND, HttpCookie.from(), body, headers);
    }

    public static Response found(HttpCookie cookie, String location) {
        return new Response(HttpStatus.FOUND, cookie, StringUtils.EMPTY, Map.of(LOCATION_HEADER_KEY, location));
    }

    public static Response found(String location) {
        return new Response(HttpStatus.FOUND, HttpCookie.from(), StringUtils.EMPTY, Map.of(LOCATION_HEADER_KEY, location));
    }

    public static Response unauthorized(String body) {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put(CONTENT_TYPE_HEADER_KEY, ContentType.HTML.getValue());
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.getBytes().length));

        return new Response(HttpStatus.UNAUTHORIZED, HttpCookie.from(), body,
            headers);
    }

    public static Response notAllowed() {
        String body = HttpStatus.METHOD_NOT_ALLOWED.getMessage();
        return new Response(HttpStatus.METHOD_NOT_ALLOWED, HttpCookie.from(), body,
            Map.of(CONTENT_TYPE_HEADER_KEY, ContentType.HTML.getValue(),
                CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.getBytes().length)));
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

    private String generateHeader() {
        return Stream.of(header.getHeaders(), Map.of(COOKIE_HEADER_KEY, cookie.toCookieHeader()))
            .flatMap(map -> map.entrySet().stream())
            .filter(header -> !header.getValue().isEmpty())
            .map(header -> String.format(HEADER_FORMAT, header.getKey(), header.getValue()))
            .collect(Collectors.joining(DELIMITER));
    }
}
