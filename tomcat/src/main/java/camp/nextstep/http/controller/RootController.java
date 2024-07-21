package camp.nextstep.http.controller;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.request.RequestMappingKey;
import camp.nextstep.http.domain.response.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;

import java.util.List;
import java.util.regex.Pattern;

public class RootController extends AbstractController implements Controller {
    private final RequestMappingKey ROOT_KEY =
            new RequestMappingKey(Pattern.compile("/"), HttpMethod.GET);

    private final List<RequestMappingKey> requestMappingKeys =
            List.of(
                    ROOT_KEY
            );

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (ROOT_KEY.isRequestMatch(request)) {
            final var responseBody = "Hello world!";
            response.successResponseByString(responseBody);
        }
    }

    @Override
    public boolean isExactHandler(HttpRequest httpRequest) {
        return requestMappingKeys.stream()
                .anyMatch(v -> v.isRequestMatch(httpRequest));
    }
}
