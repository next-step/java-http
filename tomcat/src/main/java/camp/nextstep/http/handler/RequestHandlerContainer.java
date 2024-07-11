package camp.nextstep.http.handler;

import java.util.List;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Response;

public class RequestHandlerContainer {
    private List<RequestHandler> requestHandlers;

    //TODO 이부분은 나중에 외부 주입으로
    public RequestHandlerContainer() {
        requestHandlers = List.of(
            new RootRequestHandler(),
            new LoginRequestHandler(),
            new FileRequestHandler()
        );
    }
    public Response handleRequest(List<String> requestLines) {
        RequestLine requestLine = RequestLine.createRequestLineByRequestLineStr(
            requestLines.get(0)
        );

        for (RequestHandler requestHandler : requestHandlers) {
            if (requestHandler.isMatchPathPattern(requestLine.getPath().getUrlPath())) {
                return requestHandler.makeResponse(requestLine);
            }
        }
        return Response.createNotFoundResponseByString();
    }
}
