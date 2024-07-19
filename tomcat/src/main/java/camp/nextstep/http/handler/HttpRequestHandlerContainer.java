package camp.nextstep.http.handler;

import java.io.InputStream;
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
            new RegisterHttpRequestHandler(),
            new FileHttpRequestHandler()
        );
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
