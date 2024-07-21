package camp.nextstep.http.controller;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;
import camp.nextstep.http.enums.HttpMethod;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.getHttpStartLine().getMethod() == HttpMethod.GET) {
            doGet(request, response);
            return;
        }

        if (request.getHttpStartLine().getMethod() == HttpMethod.POST) {
            doPost(request, response);
        }
    }

    protected abstract void doPost(HttpRequest request, HttpResponse response) throws Exception;
    protected abstract void doGet(HttpRequest request, HttpResponse response) throws Exception;
}
