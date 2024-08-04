package camp.nextstep.controller.strategy;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;

public class UnauthorizedStrategy implements RequestMethodStrategy {



    @Override
    public boolean matched(HttpRequest httpRequest) {
        return false;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        return Http11Response.unauthorized();
    }
}
