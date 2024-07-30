package camp.nextstep.controller.strategy;

import org.apache.coyote.http11.HttpEntity;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class NotFoundStrategy implements RequestMethodStrategy {

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return false;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return HttpEntity.notfound();
    }
}
