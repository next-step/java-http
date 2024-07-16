package camp.nextstep.controller;

import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;

public class RootController extends AbstractController {

    private static final String ROOT_PATH_BODY = "Hello world!";


    @Override
    protected void doGet(Request request, Response response) throws Exception {
        response.ok(ContentType.HTML, ROOT_PATH_BODY);
    }
}
