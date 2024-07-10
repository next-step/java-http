package camp.nextstep.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {

    @Test
    @DisplayName("HttpSessionManager 에 세션을 추가할 수 있다")
    void addSessionTest() {
        HttpSessionManager.add(new HttpSession("user"));

        assertThat(HttpSessionManager.findSession("user")).isEqualTo(new HttpSession("user"));
    }

    @Test
    @DisplayName("HttpSessionManager 에 세션을 제거할 수 있다")
    void removeSessionTest() {
        HttpSessionManager.add(new HttpSession("user"));

        HttpSessionManager.remove(new HttpSession("user"));

        assertThat(HttpSessionManager.findSession("user")).isNull();
    }
}
