package org.apache.coyote.http11;

import org.apache.coyote.http.ContentType;
import org.apache.coyote.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpResponseTest {

    @DisplayName("mimeType 없이 바디를 할당한다")
    @Test
    public void setBodyWithoutMimeType() throws Exception {
        // given
        final HttpResponse httpResponse = new HttpResponse();

        // when then
        assertDoesNotThrow(() -> httpResponse.setBody("Hello World!"));
    }

    @DisplayName("mimeType 과 함께 바디를 할당한다")
    @Test
    public void setBodyWithMimeType() throws Exception {
        // given
        final HttpResponse httpResponse = new HttpResponse();

        // when then
        assertDoesNotThrow(() -> httpResponse.setBody("Hello World!", ContentType.TEXT_HTML));
    }

}
