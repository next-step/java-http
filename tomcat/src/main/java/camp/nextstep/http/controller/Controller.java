package camp.nextstep.http.controller;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws Exception;
    boolean isExactHandler(HttpRequest httpRequest);
}
