package org.apache.coyote.handler;

import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import java.io.IOException;

import static support.OutputTest.test_css;
import static support.OutputTest.test_index_page;

class ResourceHandlerTest {

    private final Handler handler = new ResourceHandler();

    @Test
    void handle_html_response() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/index.html"));
        var response = handler.handle(request);
        test_index_page(response.toString());
    }

    @Test
    void handle_css_response() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/css/styles.css"));
        var response = handler.handle(request);
        test_css(response.toString());
    }
}