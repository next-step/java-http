package camp.nextstep.http.handler;

import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Response;

public class RootRequestHandler implements RequestHandler{
    @Override
    public boolean isMatchPathPattern(String path) {
        return "/".equals(path);
    }
    @Override
    public Response makeResponse(RequestLine requestLine) {
        final var responseBody = "Hello world!";
        return Response.createResponseByString(responseBody);
    }
}
