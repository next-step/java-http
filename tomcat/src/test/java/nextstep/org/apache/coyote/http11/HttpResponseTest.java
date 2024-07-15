package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.*;
import org.apache.session.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.HttpRequestFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    @DisplayName("sendResource 메서드는 resourcePath에 해당하는 파일을 찾아 Content-Type, Content-Length 헤더와 response body를 추가한다")
    @Test
    void sendResource() {
        HttpRequest httpRequest = HttpRequestFixture.createBy(HttpMethod.GET, "/nextstep", "", HttpHeader.of(HttpHeaderName.COOKIE.getValue(), "yummy=cookie"));
        HttpResponse httpResponse = HttpResponse.of(httpRequest, new ResourceFinder());

        httpResponse.sendResource("/nextstep.txt");
        
        String format = httpResponse.createMessage();
        assertThat(format).contains("Content-Type", "Content-Length", "nextstep");
    }

    @DisplayName("sendRedirect 메서드는 명시된 path를 Location 헤더에 추가하고, 302 Found 상태 코드를 추가한다")
    @Test
    void sendRedirect() {
        HttpRequest httpRequest = HttpRequestFixture.createBy(HttpMethod.GET, "/nextstep", "", HttpHeader.of(HttpHeaderName.COOKIE.getValue(), "yummy=cookie"));
        HttpResponse httpResponse = HttpResponse.of(httpRequest, new ResourceFinder());

        httpResponse.sendRedirect("/여기로가셈");

        String format = httpResponse.createMessage();
        assertThat(format).contains("Location", "302 Found");
    }

    @DisplayName("Session이 null이 아니고 새로운 세션이면 응답 메세지 생성 시 Set-Cookie 헤더와 JSESSION 쿠키가 포함된다. 세션의 isNew는 false가 된다.")
    @Test
    void createMessage() {
        HttpRequest httpRequest = HttpRequestFixture.createBy(HttpMethod.GET, "/nextstep", "");
        Session session = httpRequest.getSession(true);
        HttpResponse httpResponse = HttpResponse.of(httpRequest, new ResourceFinder());

        String responseMessage = httpResponse.createMessage();

        assertThat(session.isNew()).isFalse();
        assertThat(responseMessage).contains("Set-Cookie: JSESSIONID=");
    }

    @DisplayName("Session이 null이 아니고, 새로운 세션이 아니면 응답 메세지 생성 시 Set-Cookie 헤더가 포함되지 않는다.")
    @Test
    void createMessage2() {
        // 세션이 이미 생성됐고, 응답이 완료 되어 isNew = false가 된 상태
        HttpRequest httpRequest = HttpRequestFixture.createBy(HttpMethod.GET, "/nextstep", "");
        Session session = httpRequest.getSession(true);
        HttpResponse httpResponse = HttpResponse.of(httpRequest, new ResourceFinder());
        httpResponse.createMessage();

        // 같은 세션에 새로운 요청이 들어온 경우
        HttpHeader sessionHeader = HttpHeader.of(HttpHeaderName.COOKIE.getValue(), session.getId());
        HttpRequest newHttpRequest = HttpRequestFixture.createBy(HttpMethod.GET, "/nextstep", "", sessionHeader);
        HttpResponse newHttpResponse = HttpResponse.of(newHttpRequest, new ResourceFinder());

        String newResponseMessage = newHttpResponse.createMessage();

        assertThat(newResponseMessage).doesNotContain("Set-Cookie: JSESSIONID=");
    }

    @DisplayName("Session이 null이면 Set-Cookie 헤더가 포함되지 않는다.")
    @Test
    void createMessage3() {
        HttpRequest httpRequest = HttpRequestFixture.createBy(HttpMethod.GET, "/nextstep", "");
        HttpResponse httpResponse = HttpResponse.of(httpRequest, new ResourceFinder());

        String responseMessage = httpResponse.createMessage();

        assertThat(responseMessage).doesNotContain("Set-Cookie: JSESSIONID=");
    }
}
