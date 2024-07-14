package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpRequest;
import org.apache.session.Session;
import org.apache.session.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @DisplayName("HTTP Request 메세지를 파싱하여 RequestLine과 httpHeaders를 가진다.")
    @Test
    void of() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*",
                ""
        );

        HttpRequest httpRequest = HttpRequest.of(httpRequestMessage, SessionManager.create());

        assertThat(httpRequest).extracting("requestLine").isNotNull();
        assertThat(httpRequest).extracting("httpHeaders").isNotNull();
    }

    @DisplayName("getSession()은 세션이 없을 경우 null을 반환한다")
    @Test
    void getSessionNull() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*",
                ""
        );

        HttpRequest httpRequest = HttpRequest.of(httpRequestMessage, SessionManager.create());

        assertThat(httpRequest.getSession()).isNull();
    }

    @DisplayName("getSession()은 JSESSIONID 쿠키에 대해 세션이 있으면 세션을 반환한다.")
    @Test
    void getSessionNotNull() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Cookie: JSESSIONID=id",
                "Accept: */*",
                ""
        );

        SessionManager sessionManager = SessionManager.create();
        sessionManager.add(Session.from("id"));
        HttpRequest httpRequest = HttpRequest.of(httpRequestMessage, sessionManager);

        Session session = httpRequest.getSession();
        assertThat(session.getId()).isEqualTo("id");
    }


    @DisplayName("getSession()은 JSESSIONID 쿠키에 대해 세션이 없으면 null을 반환한다.")
    @Test
    void getSessionNull2() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Cookie: JSESSIONID=없는아이디",
                "Accept: */*",
                ""
        );

        SessionManager sessionManager = SessionManager.create();
        sessionManager.add(Session.from("id"));
        HttpRequest httpRequest = HttpRequest.of(httpRequestMessage, sessionManager);

        assertThat(httpRequest.getSession()).isNull();
    }

    @DisplayName("getSession(true)는 세션이 없으면 만들어서 반환하고, 자기 자신(this)의 session을 새로 등록한다.")
    @Test
    void getSessionWithTrue() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*",
                ""
        );

        HttpRequest httpRequest = HttpRequest.of(httpRequestMessage, SessionManager.create());

        Session session = httpRequest.getSession(true);
        assertThat(session).isNotNull();

        Session registeredSession = httpRequest.getSession();
        assertThat(registeredSession).isNotNull();

        assertThat(session).isEqualTo(registeredSession);
    }
}
