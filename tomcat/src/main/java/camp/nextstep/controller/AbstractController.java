package camp.nextstep.controller;

import camp.nextstep.domain.http.HttpRequest;
import camp.nextstep.domain.http.HttpResponse;

public abstract class AbstractController implements Controller {

    private static final String NOT_FOUND_PAGE_PATH = "/404.html";

    @Override
    public final HttpResponse service(HttpRequest httpRequest) {
        if (httpRequest.isGetMethod()) {
            return doGet(httpRequest);
        }
        if (httpRequest.isPostMethod()) {
            return doPost(httpRequest);
        }
        return notFoundResponse(httpRequest);
    }

    protected HttpResponse doPost(HttpRequest httpRequest) {
        return notFoundResponse(httpRequest);
    }

    protected HttpResponse doGet(HttpRequest httpRequest) {
        return notFoundResponse(httpRequest);
    }

    private HttpResponse notFoundResponse(HttpRequest httpRequest) {
        return HttpResponse.found(httpRequest.getHttpProtocol(), NOT_FOUND_PAGE_PATH);
    }
}
