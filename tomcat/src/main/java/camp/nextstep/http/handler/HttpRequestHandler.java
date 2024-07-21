package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.response.HttpResponse;

public interface HttpRequestHandler {
    HttpResponse makeResponse(RequestLine requestLine);
    boolean isExactHandler(RequestLine requestLine);
}
