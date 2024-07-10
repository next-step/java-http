package camp.nextstep.controller;

import camp.nextstep.domain.http.ContentType;
import camp.nextstep.domain.http.HttpRequest;
import camp.nextstep.domain.http.HttpResponse;

public class RootController extends AbstractController {

    private static final String ROOT_BODY = "Hello world!";

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.ok(
                httpRequest.getHttpProtocol(),
                ContentType.TEXT_HTML,
                ROOT_BODY
        );
    }
}
