package org.apache.coyote.http11;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.IOException;

import static support.OutputTest.*;

class Http11ProcessorTest {

    @Test
    void process() {
        final var socket = new StubSocket();
        final var processor = new Http11Processor(socket);

        processor.process(socket);

        test_default(socket.output());
    }

    @Test
    void index() throws IOException {
        final String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        processor.process(socket);

        test_index_page(socket.output());
    }

    @Test
    void css() throws IOException {
        final String httpRequest = String.join("\r\n",
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Accept: text/css,*/*;q=0.1",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        processor.process(socket);

        test_css(socket.output());
    }


    @Nested
    class Login {

        @Test
        void login_page() throws IOException {
            final String httpRequest = String.join("\r\n",
                    "GET /login HTTP/1.1 ",
                    "Host: localhost:8080 ",
                    "Connection: keep-alive ",
                    "",
                    "");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_login_page(socket.output());
        }

        @Test
        void correct_account_correct_password() {
            final String httpRequest = String.join("\r\n",
                    "GET /login?account=gugu&password=password HTTP/1.1 ",
                    "Host: localhost:8080 ",
                    "Connection: keep-alive ",
                    "",
                    "");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_success_redirect(socket.output());
        }

        @Test
        void correct_account_wrong_password() {
            final String httpRequest = String.join("\r\n",
                    "GET /login?account=gugu&password=wrong HTTP/1.1 ",
                    "Host: localhost:8080 ",
                    "Connection: keep-alive ",
                    "",
                    "");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_fail_redirect(socket.output());
        }

        @Test
        void wrong_account() {
            // given
            final String httpRequest = String.join("\r\n",
                    "GET /login?account=woo-yu&password=wrong HTTP/1.1 ",
                    "Host: localhost:8080 ",
                    "Connection: keep-alive ",
                    "",
                    "");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_fail_redirect(socket.output());
        }

    }

}
