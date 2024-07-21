package camp.nextstep.http.handler;

import camp.nextstep.http.controller.Controller;
import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;

import java.io.InputStream;
import java.util.List;

public class RequestMappingHandler {
    private List<Controller> controllers;

    public RequestMappingHandler(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = getController(httpRequest);
        try {
            controller.service(httpRequest, httpResponse);
        } catch (Exception ex) {
            httpResponse.internalServerErrorResponseByString();
        }
    }

    private Controller getController(HttpRequest httpRequest) {
        return controllers.stream()
                .filter(controller -> controller.isExactHandler(httpRequest))
                .findFirst()
                .get();
    }
}
