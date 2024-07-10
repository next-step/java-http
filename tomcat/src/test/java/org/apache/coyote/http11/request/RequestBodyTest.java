package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RequestBody 클래스는")
class RequestBodyTest {

        @DisplayName("RequestBody 객체를 생성할 수 있다.")
        @Test
        void create_RequestBody() {
            // given
            String param = "userId=1";
            try(BufferedReader br = new BufferedReader(new StringReader(param))){
                // when
                final var result = RequestBody.from(br, param.length());

                // then
                assertThat(result.getParameters()).hasSize(1);
                assertThat(result.getParameter("userId")).isEqualTo("1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("RequestBody가 없는 RequestBody 객체를 생성할 수 있다.")
        @Test
        void create_RequestBody_Without_RequestBody() {
            String param = "";
            try(BufferedReader br = new BufferedReader(new StringReader(param))){
                // when
                final var result = RequestBody.from(br, param.length());

                // then
                assertThat(result.getParameters()).isEmpty();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
