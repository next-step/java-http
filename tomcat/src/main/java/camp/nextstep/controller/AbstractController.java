package camp.nextstep.controller;

import camp.nextstep.domain.http.ContentType;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;
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

    private HttpResponse notFoundResponse(HttpRequest httpRequest) {
        return HttpResponse.found(httpRequest.getHttpProtocol(), NOT_FOUND_PAGE_PATH);
    }

    protected final HttpResponse handlePath(HttpRequest httpRequest) {
        return handleRequest(httpRequest, httpRequest.getFilePath());
    }

    protected final HttpResponse handleStaticPath(HttpRequest httpRequest) {
        return handleRequest(httpRequest, httpRequest.getHttpPath());
    }

    private HttpResponse handleRequest(HttpRequest httpRequest, String path) {
        String responseBody = FileUtil.readStaticPathFileResource(path, getClass());
        ContentType contentType = ContentType.fromPath(path);
        return HttpResponse.ok(httpRequest.getHttpProtocol(), contentType, responseBody);
    }
}
