package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.ContentType;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.request.RequestLineParser;
import org.apache.coyote.http11.response.ResponseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.NoSuchElementException;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

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
             final var outputStream = connection.getOutputStream();
             final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (bufferedReader.lines() == null) {
                throw new IllegalArgumentException("요청값이 빈값입니다.");
            }
            RequestLineParser requestLineParser = new RequestLineParser(bufferedReader.readLine());
            RequestLine requestLine = RequestLine.from(requestLineParser);

            ResponseResource responseResource = ResponseResource.of(requestLine.getPath());

            String extension = responseResource.parseExtension();
            String contentType = ContentType.findByExtension(extension).getContentType();
            String responseBody = responseResource.getResponseBody();

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: " + contentType + ";charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
