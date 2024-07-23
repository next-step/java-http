package camp.nextstep.http;

import java.util.Map;

public class HttpResponse {

  private final HttpStatus status;
  private final HttpHeaders headers;
  private final byte[] body;

  private HttpResponse(HttpStatus status, HttpHeaders headers, byte[] body) {
    this.status = status;
    this.headers = headers;
    this.body = body;
  }

  public static HttpResponse ok(String body, String contentType) {
    HttpHeaders headers = new HttpHeaders(Map.of("Content-Type", contentType + ";charset=utf-8"));
    return new HttpResponse(HttpStatus.OK, headers, body.getBytes());
  }

  public static HttpResponse redirect(String location) {
    HttpHeaders headers = new HttpHeaders(Map.of("Location", location));
    return new HttpResponse(HttpStatus.FOUND, headers, new byte[0]);
  }

  public static HttpResponse error(HttpStatus status, String message) {
    HttpHeaders headers = new HttpHeaders(Map.of("Content-Type", "text/plain;charset=utf-8"));
    return new HttpResponse(status, headers, message.getBytes());
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