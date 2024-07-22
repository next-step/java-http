package nextstep.org.apache.coyote.http11;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import camp.nextstep.exception.InvalidRequestException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubSocket;

class Http11ProcessorTest {

  @Test
  void process() {
    // given
    final var socket = new StubSocket();
    final var processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    var expected = String.join("\r\n",
        "HTTP/1.1 200 OK ",
        "Content-Type: text/html;charset=utf-8 ",
        "Content-Length: 12 ",
        "",
        "Hello world!");

    assertThat(socket.output()).isEqualTo(expected);
  }

  @Test
  void index() throws IOException {
    // given
    final String httpRequest = String.join("\r\n",
        "GET /index.html HTTP/1.1 ",
        "Host: localhost:8080 ",
        "Connection: keep-alive ",
        "",
        "");

    final var socket = new StubSocket(httpRequest);
    final Http11Processor processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    final URL resource = getClass().getClassLoader().getResource("static/index.html");
    var expected = String.join("\r\n",
        "HTTP/1.1 200 OK ",
        "Content-Type: text/html;charset=utf-8 ",
        "Content-Length: 556434 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
        "",
        new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

    assertThat(socket.output()).isEqualTo(expected);
  }

  @Test
  @DisplayName(" HTTP 요청이 빈 값일때 InvalidRequestException 에러를 리턴한다.")
  void requestNull() {
    // given
    String request = "";
    final var socket = new StubSocket(request);
    final var processor = new Http11Processor(socket);

    // when
    Throwable thrown = catchThrowable(() -> processor.process(socket));

    // then
    assertThat(thrown).isInstanceOf(InvalidRequestException.class);
  }

  @Test
  @DisplayName(" css요청에 대해 올바른 content-type을 리턴한다")
  void css() throws IOException {
    // given
    final String httpRequest = String.join("\r\n",
        "GET /css/styles.css HTTP/1.1 ",
        "Host: localhost:8080 ",
        "Connection: keep-alive ",
        "",
        "");

    final var socket = new StubSocket(httpRequest);
    final Http11Processor processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
    var expected = String.join("\r\n",
        "HTTP/1.1 200 OK ",
        "Content-Type: text/css;charset=utf-8 ",
        "Content-Length: 211991 ",
        "",
        new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

    assertThat(socket.output()).isEqualTo(expected);
  }

  @Test
  @DisplayName("로그인 성공시 HTTP Status Code 302를 반환하고, /index.html로 리다이렉트 한다.")
  void loginSuccess() throws IOException {
    // given
    final String httpRequest = String.join("\r\n",
        "GET /login?account=gugu&password=password HTTP/1.1 ",
        "Host: localhost:8080 ",
        "Connection: keep-alive ",
        "",
        "");

    final var socket = new StubSocket(httpRequest);
    final Http11Processor processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    var expected = String.join("\r\n",
        "HTTP/1.1 302 Found ",
        "Location: /index.html ",
        "",
        "");
    assertThat(socket.output()).isEqualTo(expected);
  }


  @Test
  @DisplayName(" 로그인 성공하면 session을 만들고, 브라우저가 set-cookie를 통해 cookie에 session 정보를  반환한다 . ")
  void cookie() {
    // given
    final String httpRequest = String.join("\r\n",
        "GET /login?account=gugu&password=password HTTP/1.1 ",
        "Host: localhost:8080 ",
        "Connection: keep-alive ",
        "",
        "");

    final var socket = new StubSocket(httpRequest);
    final Http11Processor processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    var expected = String.join("\r\n",
        "HTTP/1.1 302 Found ",
        "Set-Cookie: JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46  ",
        "Location: http://localhost:8080/index.html ",
        "",
        "");

    assertThat(socket.output()).isEqualTo(expected);
  }

  @Test
  @DisplayName(" 로그인이 되어 있을 경우, set-cookie 에 session 정보를 반환하며 index.html 페이지로 이동한다. ")
  void login() {
    // given
    final String httpRequest = String.join("\r\n",
        "GET / HTTP/1.1 ",
        "Host: localhost:8080 ",
        "Connection: keep-alive ",
        "Cookie: JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46  ",

        "",
        "");

    final var socket = new StubSocket(httpRequest);
    final Http11Processor processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
    var expected = String.join("\r\n",
        "HTTP/1.1 302 Found ",
        "Location: http://localhost:8080/index.html ",
        "",
        "");

    assertThat(socket.output()).isEqualTo(expected);
  }

  @Test
  @DisplayName(" 로그인 하지 않았을 경우, login.html 페이지로 이동한다. ")
  void isLogin() {
    // given
    final String httpRequest = String.join("\r\n",
        "GET / HTTP/1.1 ",
        "Host: localhost:8080 ",
        "Connection: keep-alive ",
        "",
        "");

    final var socket = new StubSocket(httpRequest);
    final Http11Processor processor = new Http11Processor(socket);

    // when
    processor.process(socket);

    // then
    var expected = String.join("\r\n",
        "HTTP/1.1 302 Found ",
        "Location: http://localhost:8080/login.html ",
        "",
        "");

    assertThat(socket.output()).isEqualTo(expected);
  }

}
