package camp.nextstep.controller;

import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        if (request.isGet()) {
            return doGet(request);
        }
        if (request.isPost()) {
            return doPost(request);
        }
        return HttpResponse.notFound();
    }

    protected HttpResponse doGet(HttpRequest request) {
        return HttpResponse.notFound();
    }

    protected HttpResponse doPost(HttpRequest request) {
        return HttpResponse.notFound();
    }

}
