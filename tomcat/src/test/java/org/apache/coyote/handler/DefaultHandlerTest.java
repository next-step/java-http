package org.apache.coyote.handler;

import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import static support.OutputTest.test_default;

class DefaultHandlerTest {

    private final Handler handler = new DefaultHandler();

    @Test
    void handle_response() {
        var request = new StubHttpRequest();
        var response = handler.handle(request);
        test_default(response.toString());
    }

}