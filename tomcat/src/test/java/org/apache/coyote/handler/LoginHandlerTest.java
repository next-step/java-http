package org.apache.coyote.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpPath;
import org.apache.http.header.Cookie;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import java.io.IOException;

import static support.OutputTest.*;

class LoginHandlerTest {

    private final Handler handler = new LoginHandler();

    @Test
    void correct_account_correct_password() {
        var request = new StubHttpRequest("gugu", "password");
        var response = handler.handle(request);
        test_success_redirect_setCookie(response.toString());
    }

    @Test
    void correct_account_wrong_password() {
        var request = new StubHttpRequest("gugu", "wrong");
        var response = handler.handle(request);
        test_fail_redirect(response.toString());
    }

    @Test
    void wrong_account() {
        var request = new StubHttpRequest("woo-yu", "password");
        var response = handler.handle(request);
        test_fail_redirect(response.toString());
    }

    @Test
    void login_cookie() {
        var loginResult = handler.handle(new StubHttpRequest("gugu", "password")).toString();
        var request = new StubHttpRequest(new Cookie(StringUtils.substringAfter(loginResult, "Cookie: ")));

        var response = handler.handle(request);

        test_success_redirect(response.toString());
    }
}