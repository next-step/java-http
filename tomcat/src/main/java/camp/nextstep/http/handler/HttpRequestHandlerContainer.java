package camp.nextstep.http.handler;

import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.HttpResponse;

public class HttpRequestHandlerContainer {
    private List<HttpRequestHandler> httpRequestHandlers;

    //TODO 이부분은 나중에 외부 주입으로
    public HttpRequestHandlerContainer() {
        httpRequestHandlers = List.of(
            new RootHttpRequestHandler(),
            new LoginHttpRequestHandler(),
            new FileHttpRequestHandler()
        );
    }
    public HttpResponse handleRequest(List<String> requestLines) {
        RequestLine requestLine = RequestLine.createRequestLineByRequestLineStr(
            requestLines.get(0)
        );

        return httpRequestHandlers.stream()
            .filter(v -> v.isExactHandler(requestLine))
            .findFirst()
            .map(v -> v.makeResponse(requestLine))
            .orElseGet(HttpResponse::createNotFoundResponseByString);
    }
}
