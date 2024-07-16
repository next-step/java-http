package camp.nextstep.controller;

import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.Response;

public class StaticController extends AbstractController {

    @Override
    protected void doGet(Request request, Response response) throws Exception {
        super.doGet(request, response);
    }
}
