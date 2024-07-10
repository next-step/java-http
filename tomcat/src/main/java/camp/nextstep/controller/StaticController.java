package camp.nextstep.controller;

import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;

public class StaticController extends AbstractController {

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return handleStaticPath(httpRequest);
    }
}
