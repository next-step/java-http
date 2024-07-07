package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.request.RequestLine;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

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
             final var inputReader = new BufferedReader(new InputStreamReader(inputStream));
             final var outputStream = connection.getOutputStream()) {

            RequestLine requestLine = new RequestLine(inputReader.readLine());
            requestLine.process();
            String responseBody = getResponseBody(requestLine.getPath());

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: text/html;charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getResponseBody(String path) throws IOException, URISyntaxException {
        if (path.length() <= 1) {
            return "Hello world!";
        }
        return getStaticFile(getStaticResource(path));
    }

    private URL getStaticResource(String path) {
        URL resource = getClass().getClassLoader().getResource("static" + path);
        if (resource == null) {
            throw new IllegalArgumentException("path 에 지정한 파일이 없습니다");
        }
        return resource;
    }

    private String getStaticFile(URL resource) throws IOException {
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
