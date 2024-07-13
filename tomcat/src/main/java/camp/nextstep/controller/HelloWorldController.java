package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;

import java.io.IOException;

public class HelloWorldController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.setHeader("HELLO", "World!");

        response.renderText("Hello world!", "text/html");
    }
}
