package camp.nextstep.http.handler;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;

public class RootHttpRequestHandler implements HttpRequestHandler {
    @Override
    public boolean isExactHandler(HttpRequest requestLine) {
        return "/".equals(requestLine.getHttpStartLine().getPath().getUrlPath());
    }
    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) {
        final var responseBody = "Hello world!";
        return HttpResponse.createSuccessResponseByString(responseBody);
    }
}
