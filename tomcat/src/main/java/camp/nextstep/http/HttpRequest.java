package camp.nextstep.http;

public class HttpRequest {

  private final RequestLine requestLine;
  private final HttpHeaders httpHeaders;

  public RequestLine getRequestLine() {
    return requestLine;
  }

  public HttpHeaders getHttpHeaders() {
    return httpHeaders;
  }

  public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders) {
    this.requestLine = requestLine;
    this.httpHeaders = httpHeaders;
  }

}
