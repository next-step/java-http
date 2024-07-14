package org.apache.catalina.servlets;

import org.apache.coyote.http.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.RestTemplate;
import support.TomcatServerTest;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TomcatServerTest
class DefaultServletTest {

    @DisplayName("최상위 정적 리소스를 반환한다")
    @Test
    public void rootStaticResource() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/index.html", HttpMethod.GET);

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine(HttpVersion.HTTP1_1, StatusCode.OK);
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/html", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_HTML);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).contains(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

    @DisplayName("정적 리소스를 반환한다")
    @Test
    public void staticResource() throws Exception {
        // given
        final RestTemplate restTemplate = new RestTemplate("/css/styles.css", HttpMethod.GET);

        // when
        final HttpResponse actual = restTemplate.execute();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        final HttpResponse expected = new HttpResponse();
        expected.setResponseLine(HttpVersion.HTTP1_1, StatusCode.OK);
        expected.addHeader(HttpHeader.CONTENT_TYPE, "text/css", "charset=utf-8");
        expected.setBody(new String(Files.readAllBytes(new File(resource.getFile()).toPath())), ContentType.TEXT_CSS);

        assertAll(
                () -> assertThat(actual.responseLine()).isEqualTo(expected.responseLine()),
                () -> assertThat(actual.headers()).contains(expected.headers()),
                () -> assertThat(actual.body()).isEqualTo(expected.body())
        );
    }

}
