package camp.nextstep.http.handler;

import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Response;

public interface RequestHandler {
    Response makeResponse(RequestLine requestLine);
    boolean isMatchPathPattern(String path);
}
