package org.apache.coyote;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HeaderMappingTest {

    @DisplayName("헤더를 추가한다")
    @Test
    public void addHeader() throws Exception {
        // given
        final HeaderMapping headerMapping = new HeaderMapping();

        // when then
        assertDoesNotThrow(() -> headerMapping.addHeader("Content-Type", "text/html"));
    }

    @DisplayName("헤더를 HTTP 양식에 알맞은 형식으로 변환한다")
    @Test
    public void convertHttpHeaders() throws Exception {
        // given
        final HeaderMapping headerMapping = new HeaderMapping();

        headerMapping.addHeader("Content-Type", "text/html");
        headerMapping.addHeader("Content-Length", "12");

        // when
        final String actual = headerMapping.convertHttpHeaders();

        // then
        final String header = String.join(System.lineSeparator(), "Content-Type: text/html ", "Content-Length: 12 ");
        assertThat(actual).isEqualTo(header);
    }

}
