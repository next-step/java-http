package org.apache.coyote;

import org.apache.http.header.Cookie;
import org.apache.http.session.HttpSession;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    void getSession_find() {
        var request = new StubHttpRequest(new Cookie("JSESSIONID=cookie"));

        assertThat(request.getSession(true)).isEqualTo(new HttpSession("cookie"));
    }

    @Test
    void getSession_create() {
        var request = new StubHttpRequest();

        assertThat(request.getSession(true)).isNotNull();
    }

    @Test
    void getSession_false_find() {
        var request = new StubHttpRequest(new Cookie("JSESSIONID=cookie"));

        assertThat(request.getSession(false)).isEqualTo(new HttpSession("cookie"));
    }

    @Test
    void getSession_false_cannot_create() {
        var request = new StubHttpRequest();

        assertThat(request.getSession(false)).isNull();
    }
}