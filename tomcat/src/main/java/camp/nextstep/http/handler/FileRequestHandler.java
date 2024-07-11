package camp.nextstep.http.handler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Resource;
import camp.nextstep.http.domain.Response;

/**
 * 마지막에 동작하는 핸들러로, path의 파일이 있으면 true, 없으면 notfound 반환
 */
public class FileRequestHandler implements RequestHandler {
    @Override
    public boolean isMatchPathPattern(String path) {
        return true;
    }

    @Override
    public Response makeResponse(RequestLine requestLine) {
        ClassLoader classLoader = getClass().getClassLoader();
        Resource resource = Resource.createResourceFromRequestLine(requestLine, classLoader);
        try {
            return Response.createResponseByFile(resource.getResourceFile());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                File file = new File(classLoader.getResource("static/404.html").toURI());
                return Response.createResponseByFile(file);
            } catch (IOException | URISyntaxException ex) {
                return Response.createNotFoundResponseByString();
            }
        }
    }
}
