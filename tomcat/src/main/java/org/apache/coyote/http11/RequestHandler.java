package org.apache.coyote.http11;

public class RequestHandler {
    private static final String NOT_FOUND_PATH = "/404.html";

    private final RequestHandlerMapping requestHandlerMapping;

    public RequestHandler(final RequestHandlerMapping requestHandlerMapping) {
        this.requestHandlerMapping = requestHandlerMapping;
    }

    void handle(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        if (requestHandlerMapping.isNotRegisteredPath(path)) {
            handleNotRegisteredRequest(path, httpResponse);
            return;
        }

        handleRegisteredRequest(httpRequest, httpResponse, path);
    }

    private void handleNotRegisteredRequest(final String path, final HttpResponse httpResponse) {
        try {
            httpResponse.sendResource(path);
        } catch (ResourceNotFoundException e) {
            httpResponse.sendRedirect(NOT_FOUND_PATH);
        }
    }

    private void handleRegisteredRequest(final HttpRequest httpRequest, final HttpResponse httpResponse, final String path) {
        Controller controller = requestHandlerMapping.getController(path);
        try {
            controller.service(httpRequest, httpResponse);
        } catch (ResourceNotFoundException e) {
            httpResponse.sendRedirect(NOT_FOUND_PATH);
        } catch (Exception e) {
            throw new RuntimeException(e);  // TODO: ExceptionHandler -> 예외에 따라서 처리? 아니면 status? 사용자가 예외를 커스터마이징 할 수 있는 방법?
        }
    }
}
