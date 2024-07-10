package support;

import org.apache.http.cookie.HttpCookie;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputTest {

    public static void test_default(final String output) {
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");

        assertThat(output).isEqualTo(expected);
    }

    public static void test_index_page(final String output) throws IOException {
        final URL resource = OutputTest.class.getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(output).isEqualTo(expected);
    }

    public static void test_css(final String output) throws IOException {
        final URL resource = OutputTest.class.getClassLoader().getResource("static/css/styles.css");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211991 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(output).isEqualTo(expected);
    }

    public static void test_login_page(final String output) throws IOException {
        final URL resource = OutputTest.class.getClassLoader().getResource("static/login.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3797 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(output).isEqualTo(expected);
    }

    public static void test_register_page(final String output) throws IOException {
        final URL resource = OutputTest.class.getClassLoader().getResource("static/register.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 4319 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(output).isEqualTo(expected);
    }

    public static void test_success_redirect(final String output) {
        assertThat(output).isEqualTo("HTTP/1.1 302 Found \r\nLocation: /index.html");
    }

    public static void test_success_redirect(final String output, final String cookie) {
        assertThat(output).isEqualTo("HTTP/1.1 302 Found \r\nLocation: /index.html\r\nSet-Cookie: " + cookie);
    }

    public static void test_fail_redirect(final String output) {
        assertThat(output).isEqualTo("HTTP/1.1 302 Found \r\nLocation: /401.html");
    }

}
