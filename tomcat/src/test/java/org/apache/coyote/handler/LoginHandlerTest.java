package org.apache.coyote.handler;

import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;
import support.StubLogger;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static support.OutputTest.*;

class LoginHandlerTest {

    private final Handler handler = new LoginHandler();
    private final StubLogger<LoginHandler> logger = new StubLogger<>(LoginHandler.class);

    @Test
    void get() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/login"));
        var response = handler.handle(request);
        test_login_page(response.toString());
    }

    @Test
    void get_with_param() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/login?account=gugu&password=password"));
        var response = handler.handle(request);
        test_login_page(response.toString());
    }

    @Test
    void correct_account_correct_password() {
        var request = StubHttpRequest.login("gugu", "password");
        var response = handler.handle(request);
        test_success_redirect(response.toString());
        test_log_user();
    }

    @Test
    void correct_account_wrong_password() {
        var request = StubHttpRequest.login("gugu", "wrong");
        var response = handler.handle(request);
        test_fail_redirect(response.toString());
        test_doNotLog_user();
    }

    @Test
    void wrong_account() {
        var request = StubHttpRequest.login("woo-yu", "wrong");
        var response = handler.handle(request);
        test_fail_redirect(response.toString());
        test_doNotLog_user();
    }

    private void test_log_user() {
        assertThat(logger.list.get(0).getMessage()).contains("gugu");
    }

    private void test_doNotLog_user() {
        assertThat(logger.list).isEmpty();
    }

}