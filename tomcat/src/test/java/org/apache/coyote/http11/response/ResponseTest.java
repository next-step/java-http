package org.apache.coyote.http11.response;

import org.apache.coyote.http11.meta.HttpCookie;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Response 클래스는")
class ResponseTest {

    @DisplayName("toHttp11 메서드는")
    @Nested
    class Describe_toHttp11 {
        @DisplayName("ok Response 객체를 생성할 수 있다.")
        @Test
        void create_Ok_Response() {
            // given & when
            final var result = Response.ok(ContentType.HTML, "Hello world!");

            // then
            var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html ",
                "Content-Length: 12 ",
                "",
                "Hello world!");

            assertThat(new String(result.toHttp11())).isEqualTo(expected);
        }

        @DisplayName("notFound Response 객체를 생성할 수 있다.")
        @Test
        void create_NotFound_Response() {
            // given & when
            final var result = Response.notFound(ContentType.HTML, "");

            // then
            var expected = String.join("\r\n",
                "HTTP/1.1 404 Not Found ",
                "Content-Type: text/html ",
                "Content-Length: 0 ",
                "",
                "");

            assertThat(new String(result.toHttp11())).isEqualTo(expected);
        }

        @DisplayName("found Response 객체를 생성할 수 있다.")
        @Test
        void create_Found_Response() {
            // given & when
            final var result = Response.found(HttpCookie.from(), "/users");

            // then
            var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: /users ",
                "",
                "");

            assertThat(new String(result.toHttp11())).isEqualTo(expected);
        }

        @DisplayName("unauthorized Response 객체를 생성할 수 있다.")
        @Test
        void create_Unauthorized_Response() {
            // given & when
            final var result = Response.unauthorized("Unauthorized");

            // then
            var expected = String.join("\r\n",
                "HTTP/1.1 401 Unauthorized ",
                "Content-Type: text/html ",
                "Content-Length: 12 ",
                "",
                "Unauthorized");

            assertThat(new String(result.toHttp11())).isEqualTo(expected);
        }
    }
}
