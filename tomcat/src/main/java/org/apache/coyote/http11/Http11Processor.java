package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;

import java.io.*;

import org.apache.coyote.Processor;
import org.apache.coyote.http11.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.StringJoiner;

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             final var outputStream = connection.getOutputStream()) {

            final String requestLine = readHttpRequestMessage(br);
            final HttpHeaders headers = HttpHeaderParser.parse(requestLine);
            final RequestBody requestBody = RequestBodyParser.parse(br, headers);
            final HttpRequest httpRequest = RequestLineParser.parse(requestLine, headers, requestBody);
            final HttpCookie cookie = HttpCookieParser.parse(headers);
            httpRequest.addCookie(cookie);

            final HttpResponse httpResponse = new HttpResponse(outputStream);

            final RequestHandler handler = RequestMapping.findHandler(httpRequest.getHttpPath());
            handler.handle(httpRequest, httpResponse);

        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private String readHttpRequestMessage(final BufferedReader br) throws IOException {
        StringJoiner sj = new StringJoiner("\n");
        while (true) {
            String line = br.readLine();
            log.info(line);
            if (line == null || line.isEmpty()) {
                break;
            }
            sj.add(line);
        }
        return sj.toString();
    }
}
