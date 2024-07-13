package camp.nextstep.servlet;

import org.apache.coyote.http.ContentType;
import org.apache.coyote.http.HttpHeader;
import org.apache.coyote.http.HttpMethod;
import org.apache.coyote.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.RestTemplate;
import support.ServletMapping;
import support.TomcatServerTest;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TomcatServerTest(
        servletMappings = @ServletMapping(
                path = "/register", servlet = RegisterServlet.class
        )
)
class RegisterServletTest {

    @DisplayName("GET /register 요청으로 register.html 을 반환한다")
    @Test
    public void get_register() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/register", HttpMethod.GET);

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/register.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine("HTTP/1.1 200 Ok");
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).contains(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

    @DisplayName("POST 요청으로 /register 로 회원가입 요청을 하면 index.html 을 반환한다")
    @Test
    public void post_register() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/register", HttpMethod.POST);
        restTemplate.setHeaders(Map.of(HttpHeader.CONTENT_TYPE, new String[] {ContentType.APPLICATION_FORM_URLENCODED.type()}));
        restTemplate.setParams(Map.of("account", "gugu", "password", "123", "email", "gugu@gugu.com"));

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine("HTTP/1.1 200 Ok");
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).contains(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

    @DisplayName("회원가입에 필요한 정보 중 하나라도 공백이거나 null 이면 400 을 반환한다")
    @Test
    public void post_login_fail() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/register", HttpMethod.POST);
        restTemplate.setHeaders(Map.of(HttpHeader.CONTENT_TYPE, new String[] {ContentType.APPLICATION_FORM_URLENCODED.type()}));
        restTemplate.setParams(Map.of("account", "gugu", "password", "123"));

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/register.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine("HTTP/1.1 400 Bad Request");
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).contains(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

}
