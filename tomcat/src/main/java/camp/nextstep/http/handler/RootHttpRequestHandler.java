package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.HttpResponse;

public class RootHttpRequestHandler implements HttpRequestHandler {
    @Override
    public boolean isMatchPathPattern(String path) {
        return "/".equals(path);
    }
    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        final var responseBody = "Hello world!";
        return HttpResponse.createResponseByString(responseBody);
    }
}
