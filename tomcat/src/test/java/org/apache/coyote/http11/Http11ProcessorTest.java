package org.apache.coyote.http11;

import org.apache.http.HttpPath;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;
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
        final var httpRequest = new StubHttpRequest();

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        processor.process(socket);

        test_index_page(socket.output());
    }

    @Test
    void css() throws IOException {
        final var httpRequest = new StubHttpRequest(new HttpPath("/css/styles.css"));

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        processor.process(socket);

        test_css(socket.output());
    }


    @Nested
    class Login {

        @Test
        void login_page() throws IOException {
            final var httpRequest = new StubHttpRequest(new HttpPath("/login"));

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_login_page(socket.output());
        }

        @Test
        void correct_account_correct_password() {
            final var httpRequest = new StubHttpRequest("gugu", "password");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_success_redirect(socket.output(), "JSESSIONID=cookie");
        }

        @Test
        void correct_account_wrong_password() {
            final var httpRequest = new StubHttpRequest("gugu", "wrong");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_fail_redirect(socket.output());
        }

        @Test
        void wrong_account() {
            final var httpRequest = new StubHttpRequest("woo-yu", "wrong");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_fail_redirect(socket.output());
        }

    }

    @Nested
    class Register {

        @Test
        void register_page() throws IOException {
            final var httpRequest = new StubHttpRequest(new HttpPath("/register"));

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_register_page(socket.output());
        }

        @Test
        void register() {
            final var httpRequest = new StubHttpRequest("gugu", "password", "email");

            final var socket = new StubSocket(httpRequest);
            final Http11Processor processor = new Http11Processor(socket);

            processor.process(socket);

            test_success_redirect(socket.output());
        }

    }

}
