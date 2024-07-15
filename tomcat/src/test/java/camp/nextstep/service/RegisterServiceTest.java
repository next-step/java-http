package camp.nextstep.service;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.apache.coyote.http11.request.Request;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RegisterService 클래스")
class RegisterServiceTest {

    @DisplayName("registerUser 메서드는 사용자를 등록한다.")
    @Test
    void registerUser() throws IOException {
        // given
        Request request = new Request(new BufferedReader(new StringReader("POST /login HTTP/1.1\n" +
            "Content-Length: 53\n" +
            "\n" +
            "account=gugu2&password=password&email=gugu2@abc.co.kr")));
        RegisterService registerService = new RegisterService();

        // when
        registerService.registerUser(request);
        // then
        User user = InMemoryUserRepository.findByAccount("gugu2").orElse(null);

        assertNotNull(user);
        assertEquals("gugu2", user.getAccount());
    }
}
