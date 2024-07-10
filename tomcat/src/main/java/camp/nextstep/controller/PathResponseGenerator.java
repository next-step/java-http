package camp.nextstep.controller;

import camp.nextstep.domain.http.ContentType;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;
import camp.nextstep.util.FileUtil;

public class PathResponseGenerator {

    private static final PathResponseGenerator INSTANCE = new PathResponseGenerator();

    private PathResponseGenerator() {
    }

    public static PathResponseGenerator getInstance() {
        return INSTANCE;
    }

    public HttpResponse handlePath(HttpRequest httpRequest) {
        return handleRequest(httpRequest, httpRequest.getFilePath());
    }

    public HttpResponse handleStaticPath(HttpRequest httpRequest) {
        return handleRequest(httpRequest, httpRequest.getHttpPath());
    }

    private HttpResponse handleRequest(HttpRequest httpRequest, String path) {
        String responseBody = FileUtil.readStaticPathFileResource(path, getClass());
        ContentType contentType = ContentType.fromPath(path);
        return HttpResponse.ok(httpRequest.getHttpProtocol(), contentType, responseBody);
    }
}
