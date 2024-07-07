package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final HttpRequestHeaderParser HTTP_REQUEST_HEADER_PARSER = new HttpRequestHeaderParser();
    private static final String RESOURCES_PATH = "src/main/resources/static";

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream()) {

            final HttpRequestHeader httpRequestHeader = HTTP_REQUEST_HEADER_PARSER.parse(inputStream);

            final var responseBody = readHtmlResponseBody(httpRequestHeader.requestLine());

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: text/html;charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);
            
            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String readHtmlResponseBody(final RequestLine requestLine) throws IOException {
        if (requestLine.isRootPath()) {
            return "Hello world!";
        }

        final File html = new File(RESOURCES_PATH + requestLine.url());

        return new String(Files.readAllBytes(html.toPath()));
    }
}
