package org.apache.coyote.handler;

import org.apache.coyote.HttpResponse;
import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;
import support.StubLogger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class LoginHandlerTest {

    private final Handler handler = new LoginHandler();
    private final StubLogger<LoginHandler> logger = new StubLogger<>(LoginHandler.class);

    @Test
    void default_login() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/login"));

        var response = handler.handle(request);

        test_login_page(response);
    }

    @Test
    void correct_account_correct_password() {
        var request = new StubHttpRequest(new HttpPath("/login?account=gugu&password=password"));

        var response = handler.handle(request);

        test_success_redirect(response);
        test_log_user();
    }

    @Test
    void correct_account_wrong_password() {
        var request = new StubHttpRequest(new HttpPath("/login?account=gugu&password=wrong"));

        var response = handler.handle(request);

        test_fail_redirect(response);
        test_doNotLog_user();
    }

    @Test
    void wrong_account() {
        var request = new StubHttpRequest(new HttpPath("/login?account=woo-yu&password=wrong"));

        var response = handler.handle(request);

        test_fail_redirect(response);
        test_doNotLog_user();
    }

    private void test_log_user() {
        assertThat(logger.list.get(0).getMessage()).contains("gugu");
    }

    private void test_doNotLog_user() {
        assertThat(logger.list).isEmpty();
    }

    private void test_login_page(HttpResponse response) throws IOException {
        final URL resource = getClass().getClassLoader().getResource("static/login.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3796 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(response).hasToString(expected);
    }

    private void test_success_redirect(HttpResponse response) {
        assertThat(response).hasToString("HTTP/1.1 302 Found \r\nLocation: /index.html");
    }

    private void test_fail_redirect(HttpResponse response) {
        assertThat(response).hasToString("HTTP/1.1 302 Found \r\nLocation: /401.html");
    }

}