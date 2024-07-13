package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestCookie;
import camp.nextstep.response.HttpResponse;

import java.io.IOException;

public class HelloWorldController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        addHeaderForTesting(response);

        addCookiesForTesting(response, request);

        response.renderText("Hello world!", "text/html");
    }

    private void addHeaderForTesting(HttpResponse response) {
        response.setHeader("HELLO", "World!");
    }

    private void addCookiesForTesting(HttpResponse response, HttpRequest request) {
        String cookieTestingKeyPrefix = "set-cookie-";
        int prefixLength = cookieTestingKeyPrefix.length();

        request.getQueryParameters()
                .getAllKeys().stream()
                .filter(it -> it.startsWith(cookieTestingKeyPrefix))
                .forEach(key -> {
                    String newCookieName = key.substring(prefixLength);
                    Object value = request.getQueryParameters().get(key);
                    response.setCookie(new HttpRequestCookie(newCookieName, value.toString()));
                });
    }
}
