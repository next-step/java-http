package nextstep.org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.request.RequestLineParser;
import org.apache.coyote.http11.response.ResponseResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseResourceTest {
	@DisplayName("root 경로 접근")
	@Test
	void getRandingPage() throws IOException {
		// given
		RequestLineParser requestLineParser = new RequestLineParser("GET / HTTP/1.1");
		RequestLine requestLine = RequestLine.from(requestLineParser);

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath());

		// then
		assertThat(responseResource.getUrlPath()).isEqualTo("/index.html");
	}

	@DisplayName("main.html 경로 접근")
	@Test
	void getMain() throws IOException {
		// given
		RequestLineParser requestLineParser = new RequestLineParser("GET /index.html HTTP/1.1");
		RequestLine requestLine = RequestLine.from(requestLineParser);

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath());

		// then
		assertThat(responseResource.getUrlPath()).isEqualTo("/index.html");
	}

	@DisplayName("login.html 경로 접근")
	@Test
	void getLogin() throws IOException {
		// given
		RequestLineParser requestLineParser = new RequestLineParser("GET /login?account=gugu&password=password HTTP/1.1");
		RequestLine requestLine = RequestLine.from(requestLineParser);

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath());

		// then
		assertThat(responseResource.getUrlPath()).isEqualTo("/login.html");
	}
}