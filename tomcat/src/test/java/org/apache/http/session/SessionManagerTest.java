package org.apache.http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class SessionManagerTest {
    private final SessionManager manager1 = new SessionManager();
    private final SessionManager manager2 = new SessionManager();

    @Test
    void share_session() {
        manager1.add(new HttpSession("1"));

        assertThat(manager2.findSession("1")).isNotNull();
    }

    @Test
    void find_or_create() {
        var session = manager1.findOrCreateSession(new HttpSession("3"));

        assertThat(session).isNotNull();
    }
}