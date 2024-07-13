package org.apache.coyote.http11;

import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import java.io.IOException;

import static support.OutputTest.*;

class Http11ProcessorHandlersTest {

    private final Http11ProcessorHandlers handlers = new Http11ProcessorHandlers();

    @Test
    void get_login() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/login"));
        var response = handlers.handle(request);
        test_login_page(response.toString());
    }

    @Test
    void post_login() {
        var request = new StubHttpRequest("gugu", "password");
        var response = handlers.handle(request);
        test_success_redirect_setCookie(response.toString());
    }

    @Test
    void get_register() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/register"));
        var response = handlers.handle(request);
        test_register_page(response.toString());
    }

    @Test
    void post_register() {
        var request = new StubHttpRequest("gugu2", "password2", "email");
        var response = handlers.handle(request);
        test_success_redirect(response.toString());
    }

    @Test
    void resource() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/css/styles.css"));
        var response = handlers.handle(request);
        test_css(response.toString());
    }

    @Test
    void no_match() {
        var request = new StubHttpRequest(new HttpPath("/"));
        var response = handlers.handle(request);
        test_default(response.toString());
    }
}