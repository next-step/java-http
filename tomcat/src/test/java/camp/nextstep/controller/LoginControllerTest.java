package camp.nextstep.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Controller;
import org.apache.http.HttpPath;
import org.apache.http.header.Cookie;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import static support.OutputTest.*;

class LoginControllerTest {
    
    private final Controller controller = new LoginController();

    @Test
    void get_login() throws Exception {
        var request = new StubHttpRequest(new HttpPath("/login"));
        var response = controller.service(request);
        test_login_page(response.toString());
    }

    @Test
    void correct_account_correct_password() throws Exception {
        var request = new StubHttpRequest("gugu", "password");
        var response = controller.service(request);
        test_success_redirect_setCookie(response.toString());
    }

    @Test
    void correct_account_wrong_password() throws Exception {
        var request = new StubHttpRequest("gugu", "wrong");
        var response = controller.service(request);
        test_fail_redirect(response.toString());
    }

    @Test
    void wrong_account() throws Exception {
        var request = new StubHttpRequest("woo-yu", "password");
        var response = controller.service(request);
        test_fail_redirect(response.toString());
    }

    @Test
    void login_cookie() throws Exception {
        var loginResult = controller.service(new StubHttpRequest("gugu", "password")).toString();
        var request = new StubHttpRequest(new Cookie(StringUtils.substringAfter(loginResult, "Cookie: ")));

        var response = controller.service(request);

        test_success_redirect(response.toString());
    }

}