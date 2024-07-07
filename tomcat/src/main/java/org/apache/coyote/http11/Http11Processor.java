package org.apache.coyote.http11;

import camp.nextstep.domain.http.ContentType;
import camp.nextstep.domain.http.RequestLine;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.util.FileUtil;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private static final String ROOT_PATH = "/";

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
             final var inputReader = new BufferedReader(new InputStreamReader(inputStream));
             final var outputStream = connection.getOutputStream()) {

            final var requestLine = new RequestLine(inputReader.readLine());
            final var responseBody = parseResponseBody(requestLine.getHttpPath());

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: " + parseContentType(requestLine.getHttpPath()).getContentType() + ";charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String parseResponseBody(final String path) throws IOException {
        if (path.equals(ROOT_PATH)) {
            return "Hello world!";
        }
        return FileUtil.readStaticPathFileResource(path, getClass());
    }

    private ContentType parseContentType(final String path) {
        if (path.equals(ROOT_PATH)) {
            return ContentType.TEXT_HTML;
        }
        String extension = FileUtil.parseExtension(path);
        return ContentType.fromExtension(extension);
    }
}
