package camp.nextstep.controller;

import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;

public interface Controller {

    HttpResponse service(HttpRequest httpRequest);
}
