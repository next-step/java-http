package camp.nextstep.http.handler;

import java.io.InputStream;
import java.util.List;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;

public class HttpRequestHandlerContainer {
    private List<HttpRequestHandler> httpRequestHandlers;

    public HttpRequestHandlerContainer(List<HttpRequestHandler> httpRequestHandlers) {
        this.httpRequestHandlers = httpRequestHandlers;
    }

    public HttpResponse handleRequest(InputStream inputStream) {
        HttpRequest requestLine = HttpRequest.createRequestLineByInputStream(inputStream);

        return httpRequestHandlers.stream()
                .filter(v -> v.isExactHandler(requestLine))
                .findFirst()
                .map(v -> v.makeResponse(requestLine))
                .orElseGet(HttpResponse::createNotFoundResponseByString);
    }
}
