package camp.nextstep.http.handler;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;

public interface HttpRequestHandler {
    HttpResponse makeResponse(HttpRequest requestLine);
    boolean isExactHandler(HttpRequest requestLine);
}
