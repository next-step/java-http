package camp.nextstep.http.handler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.HttpResponse;

/**
 * 마지막에 동작하는 핸들러로, path의 파일이 있으면 true, 없으면 notfound 반환
 */
public class FileHttpRequestHandler implements HttpRequestHandler {
    @Override
    public boolean isMatchPathPattern(String path) {
        return true;
    }

    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        ClassLoader classLoader = getClass().getClassLoader();
        StaticResource resource = StaticResource.createResourceFromRequestLine(requestLine, classLoader);
        try {
            return HttpResponse.createResponseByFile(resource.getResourceFile());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                File file = new File(classLoader.getResource("static/404.html").toURI());
                return HttpResponse.createResponseByFile(file);
            } catch (IOException | URISyntaxException ex) {
                return HttpResponse.createNotFoundResponseByString();
            }
        }
    }
}
