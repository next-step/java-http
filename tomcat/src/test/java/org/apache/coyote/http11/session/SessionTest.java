package org.apache.coyote.http11.session;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Session 클래스는")
class SessionTest {

    @DisplayName("세션을 생성할 수 있다.")
    @Test
    void create_Session() {
        // given
        String sessionId = "sessionId";

        // when
        Session session = new Session(sessionId);

        // then
        assertEquals(sessionId, session.getSessionId());
    }

    @DisplayName("세션에 속성을 추가할 수 있다.")
    @Test
    void add_Attribute() {
        // given
        Session session = new Session("sessionId");

        // when
        session.setAttribute("key", "value");

        // then
        assertEquals("value", session.getAttribute("key"));

    }

    @DisplayName("세션에 속성을 제거할 수 있다.")
    @Test
    void remove_Attribute() {
        // given
        Session session = new Session("sessionId");
        session.setAttribute("key", "value");

        // when
        session.removeAttribute("key");

        // then
        assertNull(session.getAttribute("key"));
    }

    @DisplayName("세션에 속성을 가져올 수 있다.")
    @Test
    void get_Attribute() {
        // given
        Session session = new Session("sessionId");
        session.setAttribute("key", "value");

        // when
        Object result = session.getAttribute("key");

        // then
        assertEquals("value", result);
    }

    @DisplayName("세션을 비교할 수 있다.")
    @Test
    void compare_Session() {
        // given
        Session session1 = new Session("sessionId");
        Session session2 = new Session("sessionId");

        // when
        boolean result = session1.equals(session2);

        // then
        assertTrue(result);
    }
}
