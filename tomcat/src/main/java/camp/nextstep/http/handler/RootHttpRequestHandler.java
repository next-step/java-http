package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.HttpResponse;

public class RootHttpRequestHandler implements HttpRequestHandler {
    @Override
    public boolean isExactHandler(RequestLine requestLine) {
        return "/".equals(requestLine.getHttpStartLine().getPath().getUrlPath());
    }
    @Override
    public HttpResponse makeResponse(RequestLine requestLine) {
        final var responseBody = "Hello world!";
        return HttpResponse.createResponseByString(responseBody);
    }
}
