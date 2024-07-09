package org.apache.coyote.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

class DefaultHandlerTest {

    private final Handler handler = new DefaultHandler();

    @Test
    void handle_response() {
        var request = new StubHttpRequest();

        var response = handler.handle(request);

        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");

        Assertions.assertThat(response).hasToString(expected);
    }

}