package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static support.OutputTest.test_success_redirect;

class RegisterHandlerTest {

    private final Handler handler = new RegisterHandler();

    @Test
    void register() {
        var request = new StubHttpRequest("gugu2", "password2", "email");
        var response = handler.handle(request);
        test_success_redirect(response.toString());
        assertThat(InMemoryUserRepository.findByAccount("gugu2").get().checkPassword("password2")).isTrue();
    }
}