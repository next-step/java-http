package nextstep.org.apache.coyote.http11.response;

import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.model.RequestBodies;
import org.apache.coyote.http11.request.model.RequestLine;
import org.apache.coyote.http11.request.parser.RequestLineParser;
import org.apache.coyote.http11.response.ResponseResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseWriterResourceTest {
	@DisplayName("root 경로 접근")
	@Test
	void getRandingPage() throws IOException {
		// given
		RequestLine requestLine = RequestLineParser.parse("GET / HTTP/1.1");

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath(),  RequestBodies.emptyRequestBodies(), requestLine.getHttpMethod(), Cookies.emptyCookies());

		// then
		assertThat(responseResource.getFilePath()).isEqualTo("/index.html");
	}

	@DisplayName("main.html 경로 접근")
	@Test
	void getMain() throws IOException {
		// given
		RequestLine requestLine = RequestLineParser.parse("GET /index.html HTTP/1.1");

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath(),  RequestBodies.emptyRequestBodies(), requestLine.getHttpMethod(), Cookies.emptyCookies());

		// then
		assertThat(responseResource.getFilePath()).isEqualTo("/index.html");
	}
}