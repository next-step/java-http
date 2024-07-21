package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.response.HttpResponse;

/**
 * 마지막에 동작하는 핸들러로, path의 파일이 있으면 true, 없으면 notfound 반환
 */
public class FileHttpRequestHandler implements HttpRequestHandler {
    @Override
    public boolean isExactHandler(RequestLine requestLine) {
        return true;
    }

    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        ClassLoader classLoader = getClass().getClassLoader();
        StaticResource resource = StaticResource.createResourceFromRequestLine(requestLine, classLoader);
        return HttpResponse.createSuccessResponseByFile(resource.getResourceFile());
    }
}
