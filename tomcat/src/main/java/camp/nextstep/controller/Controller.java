package camp.nextstep.controller;

import camp.nextstep.http.HttpRequest;
import camp.nextstep.http.HttpResponse;

public interface Controller {

  HttpResponse service(HttpRequest httpRequest) throws Exception;
}
