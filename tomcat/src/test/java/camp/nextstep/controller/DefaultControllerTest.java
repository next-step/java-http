package camp.nextstep.controller;

import org.apache.coyote.Controller;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import static support.OutputTest.test_default;

class DefaultControllerTest {

    private final Controller controller = new DefaultController();

    @Test
    void handle_response() throws Exception {
        var request = new StubHttpRequest();
        var response = controller.service(request);
        test_default(response.toString());
    }
}