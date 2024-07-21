package org.apache.coyote.http11;

import jakarta.AbstractController;
import jakarta.UserServlet;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.*;

import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    public static final ApplicationRequestHandler INSTANCE = new ApplicationRequestHandler();

    private static final Map<String, AbstractController> SERVLET_MAPPING = Map.of(
            "/login", UserServlet.INSTANCE,
            "/register", UserServlet.INSTANCE
    );

    @Override
    public HttpResponse service(HttpRequest httpRequest){
        final var requestLine = httpRequest.getRequestLine();

        if ("/".equals(requestLine.getPath())) {
            final var responseBody = "Hello world!";
            return new HttpResponse(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.OK), new HttpResponseHeaders(MimeType.TEXT_HTML), new MessageBody(responseBody.getBytes()));
        }

        final var controller = SERVLET_MAPPING.get(requestLine.getPath());
        final var httpResponse = new HttpResponse(new StatusLine(requestLine.getHttpProtocol()), httpRequest.getRequestHeaders().host());

        try {
            controller.service(httpRequest, httpResponse);
        } catch (Exception e) {
            httpResponse.setError(e.getCause(), HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        return httpResponse;
    }
}
