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
    @DisplayName("HttpSessionManager 에 세션을 가져올때 없으면 생성하고 가져올 수 있다")
    void computeIfAbsentSessionTest() {
        HttpSessionManager.computeIfAbsent("test");

        assertThat(HttpSessionManager.get("test").get()).isEqualTo(new HttpSession("test"));
    }

    @Test
    @DisplayName("HttpSessionManager 에 세션을 제거할 수 있다")
    void removeSessionTest() {
        HttpSessionManager.add(new HttpSession("user"));

        HttpSessionManager.remove(new HttpSession("user"));

        assertThat(HttpSessionManager.findSession("user")).isNull();
    }
}
