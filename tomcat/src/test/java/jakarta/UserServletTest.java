package jakarta;

import org.apache.catalina.Session;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.HttpRequestHeaders;
import org.apache.coyote.http11.request.MessageBody;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.HttpStatusCode;
import org.apache.coyote.http11.response.StatusLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServletTest {

    private UserServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UserServlet();
    }

    @Test
    @DisplayName("request header에 Cookie에 JSESSIONID 값이 없으면 response header에 새로운 값이 셋팅된다")
    public void loginCookieTest() throws IOException {

        final var requestBody = new MessageBody("account=gugu&password=password");
        final var requestLine = new RequestLine("POST /login HTTP/1.1");

        var httpServletRequest = new HttpServletRequest(requestLine, null, null, requestBody);
        var httpServletResponse = new HttpServletResponse();

        servlet.doPost(httpServletRequest, httpServletResponse);

        assertThat(httpServletResponse.getJsessionId()).isNotNull();
    }

    @Test
    @DisplayName("request header에 Cookie에 JSESSIONID 값이 있으면 response header에 새로운 값이 셋팅되지 않는다")
    public void loginCookieTest2() throws IOException {

        final var requestBody = new MessageBody("account=gugu&password=password");
        final var requestLine = new RequestLine("POST /login HTTP/1.1");
        Session session = new Session(UUID.randomUUID().toString());

        var httpServletRequest = new HttpServletRequest(requestLine, session, Cookie.ofJsessionId(session.getId()), requestBody);
        var httpServletResponse = new HttpServletResponse();

        servlet.doPost(httpServletRequest, httpServletResponse);

        assertThat(httpServletResponse.getJsessionId()).isEqualTo(session.getId());
    }


}