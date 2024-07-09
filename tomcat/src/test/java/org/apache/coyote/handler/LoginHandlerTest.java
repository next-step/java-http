package org.apache.coyote.handler;

import org.apache.coyote.HttpRequest;
import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;
import support.StubLogger;

import static org.assertj.core.api.Assertions.assertThat;

class LoginHandlerTest {

    private final Handler handler = new LoginHandler();
    private final StubLogger<LoginHandler> logger = new StubLogger<>(LoginHandler.class);

    @Test
    void correct_account_correct_password() {
        var request = new StubHttpRequest(new HttpPath("/login?account=gugu&password=password"));

        handler.handle(request);

        test_request_path(request);
        test_log_user();
    }

    @Test
    void correct_account_wrong_password() {
        var request = new StubHttpRequest(new HttpPath("/login?account=gugu&password=wrong"));

        handler.handle(request);

        test_request_path(request);
        test_doNotLog_user();
    }

    @Test
    void wrong_account() {
        var request = new StubHttpRequest(new HttpPath("/login?account=woo-yu&password=wrong"));

        handler.handle(request);

        test_request_path(request);
        test_doNotLog_user();
    }

    private void test_request_path(HttpRequest request) {
        assertThat(request.matchPath("/login.html")).isTrue();
    }

    private void test_log_user() {
        assertThat(logger.list.get(0).getMessage()).contains("gugu");
    }

    private void test_doNotLog_user() {
        assertThat(logger.list).isEmpty();
    }


}