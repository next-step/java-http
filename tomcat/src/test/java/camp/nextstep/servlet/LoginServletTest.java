package camp.nextstep.servlet;

import org.apache.coyote.http.ContentType;
import org.apache.coyote.http.HttpHeader;
import org.apache.coyote.http.HttpMethod;
import org.apache.coyote.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.RestTemplate;
import support.TomcatServerTest;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TomcatServerTest
class LoginServletTest {

    @DisplayName("GET /login 요청으로 login.html 을 반환한다")
    @Test
    public void get_login() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/login", HttpMethod.GET);

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/login.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine("HTTP/1.1 200 Ok");
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).isEqualTo(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

    @DisplayName("존재하는 회원으로 POST 로그인 /login 요청을 하면 쿠키 저장 후 index.html 로 리다이렉트 한다")
    @Test
    public void post_login_success() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/login", HttpMethod.POST);
        restTemplate.setHeaders(Map.of(HttpHeader.CONTENT_TYPE, new String[] {ContentType.APPLICATION_FORM_URLENCODED.type()}));
        restTemplate.setParams(Map.of("account", "gugu", "password", "password"));

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine("HTTP/1.1 302 Found");
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        System.out.println(actual.headers());
        System.out.println();
        System.out.println(expected.headers());

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).contains(expected.headers()).contains("Set-Cookie"),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

    @DisplayName("존재하지 않는 회원으로 POST /login 로그인 요청을 하면 401.html 반환을 한다")
    @Test
    public void post_login_fail() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/login", HttpMethod.POST);
        restTemplate.setHeaders(Map.of(HttpHeader.CONTENT_TYPE, new String[] {ContentType.APPLICATION_FORM_URLENCODED.type()}));
        restTemplate.setParams(Map.of("account", "gugu", "password", "123"));

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/401.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine("HTTP/1.1 401 Unauthorized");
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).isEqualTo(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

}