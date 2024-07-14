package camp.nextstep.controller;

import org.apache.coyote.http11.AbstractController;
import org.apache.coyote.http11.HttpRequest;
import org.apache.coyote.http11.HttpResponse;

public class IndexController extends AbstractController {
    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {

    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.sendResource("/index.html");
    }
}
