package camp.nextstep.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String CONTENT_LENGTH = "Content-Length";
  private static final String LOCATION = "Location";
  private static final String CHARSET_UTF8 = ";charset=utf-8";
  private final HttpStatus status;
  private final HttpHeaders headers;
  private final byte[] body;

  private HttpResponse(HttpStatus status, HttpHeaders headers, byte[] body) {
    this.status = status;
    this.headers = headers;
    this.body = body;
  }

  public HttpResponse addCookie(HttpCookie cookie) {
    headers.addCookie(cookie);
    return this;
  }

  public static HttpResponse ok(String body, String contentType) {
    Map<String, String> headers = new LinkedHashMap<>();
    headers.put(CONTENT_TYPE, contentType + CHARSET_UTF8);

    if (body == null) {
      return new HttpResponse(HttpStatus.OK,
          new HttpHeaders(Map.of(CONTENT_TYPE, contentType + CHARSET_UTF8)), new byte[0]);
    }

    headers.put(CONTENT_LENGTH, String.valueOf(body.getBytes().length));
    return new HttpResponse(HttpStatus.OK, new HttpHeaders(headers), body.getBytes());

  }

  public static HttpResponse redirect(String location) {
    HttpHeaders headers = new HttpHeaders(Map.of(LOCATION, location));
    return new HttpResponse(HttpStatus.FOUND, headers, new byte[0]);
  }

  public static HttpResponse error(HttpStatus status, String message) {
    HttpHeaders headers = new HttpHeaders(Map.of(CONTENT_TYPE, "text/plain;charset=utf-8"));
    return new HttpResponse(status, headers, message.getBytes());
  }

  public static HttpResponse notFound(HttpStatus status, String message) {
    HttpHeaders headers = new HttpHeaders(Map.of(CONTENT_TYPE, "text/plain;charset=utf-8"));
    return new HttpResponse(HttpStatus.NOT_FOUND, headers, message.getBytes());
  }

  public String buildResponse() {
    StringBuilder response = new StringBuilder();
    response.append(
        String.format("HTTP/1.1 %s %s\r\n", status.getStatusCode(), status.getReasonPhrase()));
    response.append(headers.toString());
    response.append("\r\n");
    if (body.length > 0) {
      response.append(new String(body));
    }
    return response.toString();
  }

}