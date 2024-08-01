package camp.nextstep.controller.strategy;

import org.apache.coyote.http11.HttpEntity;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.HttpResponse;

public class IndexGetStrategy implements RequestMethodStrategy {
    private final String INDEX = "static/index.html";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
                .equals(RequestMethod.GET.name());
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return HttpEntity.resource("1.1", INDEX);
    }
}
