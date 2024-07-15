package camp.nextstep.controller;

import org.apache.coyote.AbstractController;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.coyote.RequestMapping;
import org.apache.http.body.HttpTextBody;

public class DefaultController extends AbstractController {
    private static final String MAPPING_PATH = "/";
    private static final String MESSAGE = "Hello world!";

    public DefaultController() {
        super(MAPPING_PATH);
        RequestMapping.addDefault(this);
    }

    @Override
    public HttpResponse service(final HttpRequest request) {
        final var responseBody = new HttpTextBody(MESSAGE);

        return new HttpResponse(responseBody);
    }
}
