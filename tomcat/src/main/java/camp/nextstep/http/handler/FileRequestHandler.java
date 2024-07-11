package camp.nextstep.http.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Resource;
import camp.nextstep.http.domain.Response;

/**
 * 마지막에 동작하는 핸들러로, path의 파일이 있으면 true, 없으면 notfound 반환
 */
public class FileRequestHandler implements RequestHandler{
    @Override
    public boolean isMatchPathPattern(String path) {
        return true;
    }
    @Override
    public Response makeResponse(RequestLine requestLine) {
        Resource resource = Resource.createResourceFromRequestLine(requestLine, getClass().getClassLoader());
        try {
            return Response.createResponseByFile(resource.getResourceFile());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                return Response.createResponseByFile(new File("static/404.html"));
            } catch (IOException ex) {
                return Response.createNotFoundResponseByString();
            }
        }
    }
}
