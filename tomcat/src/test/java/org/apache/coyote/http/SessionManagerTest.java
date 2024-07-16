package org.apache.coyote.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    @DisplayName("동시에 세션 매니져 컬렉션에 접근해도 정상적으로 처리 한다.")
    @Test
    public void concurrencyTest() throws Exception {
        // given
        final int NUM_OF_SESSION = 1000;
        final String SESSION_ID_PREFIX = "session-";
        final SessionManager sessionManager = new SessionManager();
        final ExecutorService executorService = Executors.newFixedThreadPool(250);
        final CountDownLatch latch = new CountDownLatch(NUM_OF_SESSION);

        final Collection<Session> actual = new ConcurrentLinkedDeque<>();

        // when
        for (int i = 0; i < NUM_OF_SESSION; i++) {
            final String sessionId = SESSION_ID_PREFIX + i;

            executorService.submit(() -> {
                try {
                    final Session session = new Session(sessionId);
                    sessionManager.add(session);
                    final Session retrievedSession = sessionManager.findSession(sessionId);
                    actual.add(retrievedSession);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        assertThat(actual).hasSize(NUM_OF_SESSION);
    }

}
