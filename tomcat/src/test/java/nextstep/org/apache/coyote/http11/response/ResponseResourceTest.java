package nextstep.org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.model.RequestBodies;
import org.apache.coyote.http11.request.model.RequestLine;
import org.apache.coyote.http11.request.RequestLineParser;
import org.apache.coyote.http11.response.ResponseResource;
import org.apache.coyote.http11.response.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResponseResourceTest {
	@DisplayName("root 경로 접근")
	@Test
	void getRandingPage() throws IOException {
		// given
		RequestLine requestLine = RequestLineParser.parse("GET / HTTP/1.1");

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath(), RequestBodies.emptyRequestBodies(), requestLine.getHttpMethod());

		// then
		assertThat(responseResource.getFilePath()).isEqualTo("/index.html");
	}

	@DisplayName("main.html 경로 접근")
	@Test
	void getMain() throws IOException {
		// given
		RequestLine requestLine = RequestLineParser.parse("GET /index.html HTTP/1.1");

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath(), RequestBodies.emptyRequestBodies(), requestLine.getHttpMethod());

		// then
		assertThat(responseResource.getFilePath()).isEqualTo("/index.html");
	}

	@DisplayName("login 성공 시 index.html로 이동")
	@Test
	void getLogin() throws IOException {
		// given
		String account = "gugu";
		String password = "password";
		RequestLine requestLine = RequestLineParser.parse("GET /login?account=" + account + "&password=" + password + " HTTP/1.1");

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath(), RequestBodies.emptyRequestBodies(),requestLine.getHttpMethod());

		// then
		assertAll(
			() -> assertThat(responseResource.getFilePath()).isEqualTo("/index.html"),
			() -> assertThat(responseResource.getStatusCode().name()).isEqualTo(StatusCode.FOUND.name())
		);
	}

	@DisplayName("login 실패 시 401.html로 이동")
	@Test
	void failLogin() throws IOException {
		// given
		String account = "gugu";
		String password = "password1212";
		RequestLine requestLine = RequestLineParser.parse("GET /login?account=" + account + "&password=" + password + " HTTP/1.1");

		// when
		ResponseResource responseResource = ResponseResource.of(requestLine.getPath(), RequestBodies.emptyRequestBodies(), requestLine.getHttpMethod());

		// then
		assertAll(
				() -> assertThat(responseResource.getFilePath()).isEqualTo("/401.html"),
				() -> assertThat(responseResource.getStatusCode().name()).isEqualTo(StatusCode.NOT_FOUND.name())
		);
	}
}