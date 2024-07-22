package camp.nextstep.http;

public class HttpRequest {

  private final RequestLine requestLine;
  private final HttpHeaders headers;
  private final String requestBody;

  public HttpRequest(RequestLine requestLine, HttpHeaders headers, String requestBody) {
    this.requestLine = requestLine;
    this.headers = headers;
    this.requestBody = requestBody;
  }

  public RequestLine getRequestLine() {
    return requestLine;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public String getRequestBody() {
    return requestBody;
  }
}
