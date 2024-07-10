package camp.nextstep.controller;

import camp.nextstep.domain.http.ContentType;
import camp.nextstep.domain.http.HttpRequest;
import camp.nextstep.domain.http.HttpResponse;
import camp.nextstep.util.FileUtil;

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

    protected final HttpResponse handleStaticPath(HttpRequest httpRequest) {
        String responseBody = FileUtil.readStaticPathFileResource(httpRequest.getFilePath(), getClass());
        ContentType contentType = ContentType.fromPath(httpRequest.getFilePath());
        return HttpResponse.ok(httpRequest.getHttpProtocol(), contentType, responseBody);
    }

    private HttpResponse notFoundResponse(HttpRequest httpRequest) {
        return HttpResponse.found(httpRequest.getHttpProtocol(), NOT_FOUND_PAGE_PATH);
    }
}
