package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.HttpResponse;

public interface HttpRequestHandler {
    HttpResponse makeResponse(RequestLine requestLine);
    boolean isMatchPathPattern(String path);
}
