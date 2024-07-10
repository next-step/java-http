package org.apache.coyote.http11;

import org.apache.coyote.http.ContentType;
import org.apache.coyote.http.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ResponseTest {

    @DisplayName("mimeType 없이 바디를 할당한다")
    @Test
    public void setBodyWithoutMimeType() throws Exception {
        // given
        final Response response = new Response();

        // when then
        assertDoesNotThrow(() -> response.setBody("Hello World!"));
    }

    @DisplayName("mimeType 과 함께 바디를 할당한다")
    @Test
    public void setBodyWithMimeType() throws Exception {
        // given
        final Response response = new Response();

        // when then
        assertDoesNotThrow(() -> response.setBody("Hello World!", ContentType.TEXT_HTML));
    }

}
