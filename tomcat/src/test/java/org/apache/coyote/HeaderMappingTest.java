package org.apache.coyote;

import org.apache.coyote.http.ContentType;
import org.apache.coyote.http.HeaderMapping;
import org.apache.coyote.http.HttpHeader;
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
        assertDoesNotThrow(() -> headerMapping.addHeader(HttpHeader.CONTENT_TYPE, "text/html"));
    }

    @DisplayName("헤더를 HTTP 양식에 알맞은 형식으로 변환한다")
    @Test
    public void convertHttpHeaders() throws Exception {
        // given
        final HeaderMapping headerMapping = new HeaderMapping();

        headerMapping.addHeader(HttpHeader.CONTENT_TYPE, ContentType.TEXT_HTML.type());
        headerMapping.addHeader(HttpHeader.CONTENT_LENGTH, "12");

        // when
        final String actual = headerMapping.convertHttpHeaders();

        // then
        final String header = String.join(System.lineSeparator(), "Content-Type: text/html ", "Content-Length: 12 ");
        assertThat(actual).isEqualTo(header);
    }

}
