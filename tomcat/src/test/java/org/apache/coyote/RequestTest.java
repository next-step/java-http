package org.apache.coyote;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class RequestTest {
    @Test
    void parse_get() {
        final var request = new Request("GET /users HTTP/1.1 ");

        assertSoftly(softly -> {
            softly.assertThat(request.method).isEqualTo(RequestMethod.GET);
            softly.assertThat(request.path).isEqualTo("/users");
            softly.assertThat(request.protocol).isEqualTo("HTTP");
            softly.assertThat(request.protocolVersion).isEqualTo("1.1");
        });
    }

    @Test
    void parse_post() {
        final var request = new Request("POST /users HTTP/1.1 ");

        assertSoftly(softly -> {
            softly.assertThat(request.method).isEqualTo(RequestMethod.POST);
            softly.assertThat(request.path).isEqualTo("/users");
            softly.assertThat(request.protocol).isEqualTo("HTTP");
            softly.assertThat(request.protocolVersion).isEqualTo("1.1");
        });

    }

    @Test
    void parse_params() {
        final var request = new Request("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ");

        assertSoftly(softly -> {
            softly.assertThat(request.method).isEqualTo(RequestMethod.GET);
            softly.assertThat(request.path).isEqualTo("/users");
            softly.assertThat(request.protocol).isEqualTo("HTTP");
            softly.assertThat(request.protocolVersion).isEqualTo("1.1");
            softly.assertThat(request.params.get("userId")).isEqualTo("javajigi");
            softly.assertThat(request.params.get("password")).isEqualTo("password");
            softly.assertThat(request.params.get("name")).isEqualTo("JaeSung");
        });
    }
}