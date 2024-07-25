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
    return HttpResponse.notAllowedMethod();
  }

  protected HttpResponse doPost(HttpRequest request) throws Exception { /* NOOP */
    return HttpResponse.notAllowedMethod();
  }

  protected HttpResponse doGet(HttpRequest request) throws Exception { /* NOOP */
    return HttpResponse.notAllowedMethod();
  }
}
