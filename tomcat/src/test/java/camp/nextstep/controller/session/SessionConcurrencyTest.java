package camp.nextstep.controller.session;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.model.User;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionConcurrencyTest {
    @Test
    @DisplayName("SessionManager의 동시성을 테스트한다.")
    void findConcurrentSession() throws InterruptedException {
        int threadNumber = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        CountDownLatch latch = new CountDownLatch(1000);

        for (int i = 0; i < threadNumber; i++){
            executorService.execute(
                () -> {
                    Session sessionCreated = new Session(
                        UUID.randomUUID(), new User("account", "password", "email"));
                    SessionManager.add(sessionCreated);
                    latch.countDown();
                }
            );
        }

        latch.await();
        executorService.shutdown();

        assertThat(threadNumber).isEqualTo(SessionManager.getSize());
    }
}
