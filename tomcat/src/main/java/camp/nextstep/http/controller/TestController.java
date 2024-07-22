package camp.nextstep.http.controller;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.request.RequestMappingKey;
import camp.nextstep.http.domain.response.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;

import java.util.regex.Pattern;

public class TestController extends AbstractController implements Controller {
    private final RequestMappingKey TEST_KEY =
            new RequestMappingKey(Pattern.compile("/test"), HttpMethod.GET);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        System.out.println("sleep");
        Thread.sleep(100000);
        System.out.println("awake");
    }

    @Override
    public boolean isExactHandler(HttpRequest httpRequest) {
        return TEST_KEY.isRequestMatch(httpRequest);
    }
}
