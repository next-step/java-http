package camp.nextstep.controller.strategy;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public interface RequestMethodStrategy {

    boolean matched(HttpRequest httpRequest);

    HttpResponse serve(HttpRequest httpRequest);
}
