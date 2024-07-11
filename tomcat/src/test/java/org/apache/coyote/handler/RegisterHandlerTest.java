package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static support.OutputTest.*;

class RegisterHandlerTest {

    private final Handler handler = new RegisterHandler();

    @Test
    void get() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/register"));
        var response = handler.handle(request);
        test_register_page(response.toString());
    }

    @Test
    void get_with_param() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/register?account=gugu&password=password"));
        var response = handler.handle(request);
        test_register_page(response.toString());
    }

    @Test
    void register() {
        var request = new StubHttpRequest("gugu2", "password2", "email");
        var response = handler.handle(request);
        test_success_redirect(response.toString());
        assertThat(InMemoryUserRepository.findByAccount("gugu2").get().checkPassword("password2")).isTrue();
    }
}