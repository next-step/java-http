package org.apache.coyote;

import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubController;
import support.StubHttpRequest;

import java.io.IOException;

import static support.OutputTest.test_css;
import static support.OutputTest.test_default;

class RequestMappingTest {
    static {
        RequestMapping.addDefault(new StubController(false));
    }

    @Test
    void no_mapping_to_resource() throws IOException {
        var requestMapping = new RequestMapping();
        var request = new StubHttpRequest(new HttpPath("/css/styles.css"));

        var output = requestMapping.handle(request);
        test_css(output.toString());
    }

    @Test
    void mapping() {
        var requestMapping = new RequestMapping();
        var request = new StubHttpRequest(new HttpPath("/"));

        var output = requestMapping.handle(request);
        test_default(output.toString());
    }

    @Test
    void default_mapping() {
        var requestMapping = new RequestMapping();
        var request = new StubHttpRequest(new HttpPath("/no-mapping"));

        var output = requestMapping.handle(request);
        test_default(output.toString());
    }
}