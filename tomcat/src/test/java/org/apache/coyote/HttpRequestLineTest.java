package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.HttpPath;
import org.apache.http.HttpProtocol;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class HttpRequestLineTest {
    @Test
    void parse_get() {
        final var request = new HttpRequestLine("GET /users HTTP/1.1 ");

        assertSoftly(softly -> {
            softly.assertThat(request.method).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.path).isEqualTo(new HttpPath("/users"));
            softly.assertThat(request.protocol).isEqualTo(HttpProtocol.HTTP_11);
        });
    }

    @Test
    void parse_post() {
        final var request = new HttpRequestLine("POST /users HTTP/1.1 ");

        assertSoftly(softly -> {
            softly.assertThat(request.method).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.path).isEqualTo(new HttpPath("/users"));
            softly.assertThat(request.protocol).isEqualTo(HttpProtocol.HTTP_11);
        });

    }

    @Test
    void parse_params() {
        final var request = new HttpRequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ");

        assertSoftly(softly -> {
            softly.assertThat(request.method).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.path).isEqualTo(new HttpPath("/users"));
            softly.assertThat(request.protocol).isEqualTo(HttpProtocol.HTTP_11);
            softly.assertThat(request.params.get("userId")).isEqualTo("javajigi");
            softly.assertThat(request.params.get("password")).isEqualTo("password");
            softly.assertThat(request.params.get("name")).isEqualTo("JaeSung");
        });
    }
}