package support;

import org.apache.coyote.AbstractController;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.http.body.HttpTextBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StubController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(StubController.class);
    private static final String MAPPING_PATH = "/";
    private static final String MESSAGE = "Hello world!";

    private final boolean testQueue;

    public StubController(boolean testQueue) {
        super(MAPPING_PATH);
        this.testQueue = testQueue;
    }

    @Override
    public HttpResponse service(final HttpRequest request) throws InterruptedException {
        log.info("connected");
        if (testQueue) {
            Thread.sleep(400);
        } else {
            Thread.sleep(600);
        }
        final var responseBody = new HttpTextBody(MESSAGE);

        log.info("response");
        return new HttpResponse(responseBody);
    }
}
