package camp.nextstep.controller;

import camp.nextstep.domain.http.HttpRequest;
import camp.nextstep.domain.http.HttpResponse;

public class StaticController extends AbstractController {

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return handleStaticPath(httpRequest);
    }
}
