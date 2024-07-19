package org.apache.coyote.http11;

import jakarta.MyServlet;
import jakarta.UserServlet;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.*;

import java.io.IOException;
import java.util.Map;

public class ApplicationRequestHandler implements RequestHandler {

    public static final ApplicationRequestHandler INSTANCE = new ApplicationRequestHandler();

    private static final Map<String, MyServlet> SERVLET_MAPPING = Map.of(
            "/login", UserServlet.INSTANCE,
            "/register", UserServlet.INSTANCE
    );

    @Override
    public HttpResponse service(HttpRequest httpRequest) throws IOException {
        final var requestLine = httpRequest.getRequestLine();

        if ("/".equals(requestLine.getPath())) {
            final var responseBody = "Hello world!";
            return new HttpResponse(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.OK), new HttpResponseHeaders(MimeType.TEXT_HTML), new MessageBody(responseBody.getBytes()));
        }

        final var servlet = SERVLET_MAPPING.get(requestLine.getPath());
        final var httpResponse = new HttpResponse(new StatusLine(requestLine.getHttpProtocol(), null));

        servlet.delegate(httpRequest, httpResponse);

        return httpResponse;
    }
}
