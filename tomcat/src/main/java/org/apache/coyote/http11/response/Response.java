package org.apache.coyote.http11.response;

public class Response {

    private static final String DELIMITER = "\r\n";
    private static final String HTTP11_VERSION = "HTTP/1.1";

    HttpStatus status;
    ContentType contentType;
    String body;

    private Response(HttpStatus status, ContentType contentType, String body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public static Response ok(ContentType contentType, String body) {
        return new Response(HttpStatus.OK, contentType, body);
    }

    public static Response notFound(String body) {
        return new Response(HttpStatus.NOT_FOUND, ContentType.HTML, body);
    }

    public static Response redirect(String responseBody) {
        return new Response(HttpStatus.FOUND, ContentType.HTML, responseBody);
    }

    public static Response unauthorized(String responseBody) {
        return new Response(HttpStatus.UNAUTHORIZED, ContentType.HTML, responseBody);
    }

    public byte[] toHttp11() {
        String statusLine = String.format("%s %d %s ", HTTP11_VERSION, status.getCode(), status.getMessage());
        String contentTypeHeader = String.format("Content-Type: %s ", contentType.getValue());
        String contentLengthHeader = String.format("Content-Length: %d ", body.getBytes().length);

        return String.join(DELIMITER,
            statusLine,
            contentTypeHeader,
            contentLengthHeader,
            "",
            body).getBytes();
    }
}
