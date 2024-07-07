package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.request.Request;
import camp.nextstep.request.RequestParser;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private RequestParser requestParser;
    private StaticResourceLoader staticResourceLoader;

    public Http11Processor(final Socket connection) {
        this.connection = connection;

        this.requestParser = new RequestParser();
        this.staticResourceLoader = new StaticResourceLoader();
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var inputStreamReader = new InputStreamReader(inputStream);
             final var bufferedReader = new BufferedReader(inputStreamReader);
             final var outputStream = connection.getOutputStream();
        ) {

            Request request = requestParser.parse(bufferedReader);

            String responseBody = getResponseBody(request.getPath());

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: " + request.getPredictedMimeType() + ";charset=utf-8 ",
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
        return staticResourceLoader.readAllLines(path);
    }
}
