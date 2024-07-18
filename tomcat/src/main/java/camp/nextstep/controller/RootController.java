package camp.nextstep.controller;

import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.response.HttpResponse;
import org.apache.coyote.response.HttpStatus;
import org.apache.coyote.response.MimeType;

public class RootController extends AbstractController {
    private static final String ROOT_CONTENT = "Hello world!";

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        return new HttpResponse(
                HttpStatus.OK,
                MimeType.HTML,
                ROOT_CONTENT);
    }
}
