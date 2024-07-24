package camp.nextstep.controller;

import camp.nextstep.exception.UnsupportedMethodException;
import camp.nextstep.http.HttpRequest;
import camp.nextstep.http.HttpResponse;

public class AbstractController implements Controller {

  @Override
  public HttpResponse service(HttpRequest httpRequest) throws Exception {

    if (httpRequest.getRequestLine().isGetMethod()) {
      return doGet(httpRequest);
    }
    if (httpRequest.getRequestLine().isPostMethod()) {
      return doPost(httpRequest);
    }
    throw new UnsupportedMethodException("지원하지 않는 HTTP Method 입니다.");
  }

  protected HttpResponse doPost(HttpRequest request) throws Exception { /* NOOP */
    throw new UnsupportedMethodException(
        "지원하지 않는 HTTP Method 입니다 : " + request.getRequestLine().getHttpMethod());
  }

  protected HttpResponse doGet(HttpRequest request) throws Exception { /* NOOP */

    throw new UnsupportedMethodException(
        "지원하지 않는 HTTP Method 입니다 : " + request.getRequestLine().getHttpMethod());
  }
}
