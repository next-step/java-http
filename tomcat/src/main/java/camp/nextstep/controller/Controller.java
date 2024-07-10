package camp.nextstep.controller;

import camp.nextstep.domain.http.HttpRequest;
import camp.nextstep.domain.http.HttpResponse;

public interface Controller {

    HttpResponse service(HttpRequest httpRequest);
}
