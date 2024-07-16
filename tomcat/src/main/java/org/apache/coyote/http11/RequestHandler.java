package org.apache.coyote.http11;

public class RequestHandler {
    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String INTERNAL_SERVER_ERROR_PATH = "/500.html";

    private final RequestHandlerMapping requestHandlerMapping;

    public RequestHandler(final RequestHandlerMapping requestHandlerMapping) {
        this.requestHandlerMapping = requestHandlerMapping;
    }

    void handle(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        try {
            handleRequest(httpRequest, httpResponse, path);
        } catch (ResourceNotFoundException e) {
            httpResponse.sendRedirect(NOT_FOUND_PATH);
        } catch (Exception e) {
            httpResponse.sendRedirect(INTERNAL_SERVER_ERROR_PATH);
            throw new RuntimeException(e);
        }
    }

    private void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse, final String path) throws Exception {
        if (requestHandlerMapping.isNotRegisteredPath(path)) {
            httpResponse.sendResource(path);
            return;
        }

        Controller controller = requestHandlerMapping.getController(path);
        controller.service(httpRequest, httpResponse);
    }
}
