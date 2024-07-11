package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;

public class HelloWorldController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.renderText("Hello world!");
    }
}
