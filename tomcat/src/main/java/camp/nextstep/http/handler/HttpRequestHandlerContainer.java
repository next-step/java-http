package camp.nextstep.http.handler;

import java.io.InputStream;
import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.HttpResponse;

public class HttpRequestHandlerContainer {
    private List<HttpRequestHandler> httpRequestHandlers;

    public HttpRequestHandlerContainer(List<HttpRequestHandler> httpRequestHandlers) {
        this.httpRequestHandlers = httpRequestHandlers;
    }

    public HttpResponse handleRequest(InputStream inputStream) {
        RequestLine requestLine = RequestLine.createRequestLineByInputStream(inputStream);

        return httpRequestHandlers.stream()
                .filter(v -> v.isExactHandler(requestLine))
                .findFirst()
                .map(v -> v.makeResponse(requestLine))
                .orElseGet(HttpResponse::createNotFoundResponseByString);
    }
}
