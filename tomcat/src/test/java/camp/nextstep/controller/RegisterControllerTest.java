package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.coyote.Controller;
import org.apache.http.HttpPath;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static support.OutputTest.test_register_page;
import static support.OutputTest.test_success_redirect;

class RegisterControllerTest {
    private final Controller controller = new RegisterController();

    @Test
    void get_register() throws Exception {
        var request = new StubHttpRequest(new HttpPath("/register"));
        var response = controller.service(request);
        test_register_page(response.toString());
    }

    @Test
    void register() throws Exception {
        var request = new StubHttpRequest("gugu2", "password2", "email");
        var response = controller.service(request);
        test_success_redirect(response.toString());
        assertThat(InMemoryUserRepository.findByAccount("gugu2").get().checkPassword("password2")).isTrue();
    }

}