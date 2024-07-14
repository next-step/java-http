package org.apache.coyote.http11;

import org.apache.coyote.*;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.session.HttpSession;
import org.apache.http.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Http11Processor implements Runnable, Processor {
    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final RequestMapping handlers = new RequestMapping();
    private final SessionManager sessionManager = new SessionManager();

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
            final var request = parseRequest(inputReader);
            final var response = handlers.handle(request);

            outputStream.write(response.toString().getBytes());
            outputStream.flush();
        } catch (IOException | NotSupportHttpRequestException e) {
            log.error(e.getMessage(), e);
        }
    }

    private HttpRequest parseRequest(BufferedReader reader) throws IOException {
        final var requestLine = new HttpRequestLine(reader.readLine().trim());
        final var headers = new HttpRequestHeaders(parseHeaders(reader));
        final var body = headers.parseBody(parseBody(reader, headers.contentLength()));
        final var session = sessionManager.findSession(headers.getSession());
        final Function<Boolean, HttpSession> getSession = (canCreate) -> {
            if (session != null) {
                return session;
            }
            if (!canCreate) {
                return null;
            }
            return sessionManager.create();
        };

        return new HttpRequest(requestLine, headers, body, getSession);
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
