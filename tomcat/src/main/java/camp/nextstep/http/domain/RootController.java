package camp.nextstep.http.domain;

import java.io.IOException;

public class RootController extends AbstractController {
    private static final String DEFAULT_MESSAGE = "Hello world!";

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        response.setContentType(ContentType.HTML);
        response.forwardBody(DEFAULT_MESSAGE);
    }

}
