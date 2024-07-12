package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.RequestBodiesParser;
import org.apache.coyote.http11.request.model.RequestBodies;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestBodiesParserTest {

    @DisplayName("requestBodies 파싱하기")
    @Test
    public void parseRequestBodies() {
        //given
        String requestBody = "name=hello&age=20";
        //when
        RequestBodies requestBodies = RequestBodiesParser.parse(requestBody);

        //then
        assertAll(
                () -> assertThat(requestBodies.getRequestBodyValueByKey("name")).isEqualTo("hello"),
                () -> assertThat(requestBodies.getRequestBodyValueByKey("age")).isEqualTo("20")
        );
    }
}