package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.model.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

	@DisplayName("html 파일 확장자명을 ContentType으로 반환한다.")
	@Test
	void convertHtmlToContentType() {
		// given
		String input = ".html";

		// when
		ContentType contentType = ContentType.findByExtension(input);

		// then
		assertThat(contentType.getContentType()).isEqualTo("text/html");
	}

	@DisplayName("css 파일 확장자명을 ContentType으로 반환한다.")
	@Test
	void convertCssToContentType() {
		// given
		String input = ".css";

		// when
		ContentType contentType = ContentType.findByExtension(input);

		// then
		assertThat(contentType.getContentType()).isEqualTo("text/css");
	}

	@DisplayName("js 파일 확장자명을 ContentType으로 반환한다.")
	@Test
	void convertJsToContentType() {
		// given
		String input = ".js";

		// when
		ContentType contentType = ContentType.findByExtension(input);

		// then
		assertThat(contentType.getContentType()).isEqualTo("application/javascript");
	}
}