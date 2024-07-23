package camp.nextstep.http;

import org.apache.coyote.http11.HttpRequestBody;

public class HttpRequest {

  private final RequestLine requestLine;
  private final HttpHeaders headers;
  private final HttpRequestBody requestBody;

  public HttpRequest(RequestLine requestLine, HttpHeaders headers, HttpRequestBody requestBody) {
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

  public HttpRequestBody getRequestBody() {
    return requestBody;
  }
}
