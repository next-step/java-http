package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpRequestLine;
import org.apache.coyote.Processor;
import org.apache.http.header.HttpRequestHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Http11Processor implements Runnable, Processor {
    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final Http11ProcessorHandlers handlers = new Http11ProcessorHandlers();

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
        try (final var inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             final var outputStream = connection.getOutputStream()) {
            final var requestLine = new HttpRequestLine(inputReader.readLine().trim());
            final var headers = new HttpRequestHeaders(parseHeaders(inputReader));
            final var body = headers.parseBody(parseBody(inputReader, headers.contentLength()));
            final var request = new HttpRequest(requestLine, headers, body);

            final var response = handlers.handle(request);

            outputStream.write(response.toString().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<String> parseHeaders(BufferedReader reader) throws IOException {
        var lines = new ArrayList<String>();
        while (reader.ready()) {
            var line = reader.readLine();
            if (line.isEmpty()) {
                break;
            }
            lines.add(line.trim());
        }
        return lines;
    }

    private String parseBody(BufferedReader reader, int contentLength) throws IOException {
        if (contentLength == -1) {
            return null;
        }

        char[] body = new char[contentLength];
        reader.read(body);

        return new String(body);
    }
}
