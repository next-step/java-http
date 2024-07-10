package org.apache.coyote.http11;

import camp.nextstep.controller.Controller;
import camp.nextstep.controller.RequestMapping;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.*;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private final Socket connection;
    private final RequestMapping requestMapping;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
        this.requestMapping = new RequestMapping();
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
            final HttpRequest httpRequest = new HttpRequest(inputStream);
            final HttpResponse httpResponse = new HttpResponse(outputStream);

            initSessionIfNecessary(httpRequest, httpResponse);

            final Controller controller = requestMapping.getController(httpRequest.getPath());

            if (controller != null) {
                controller.service(httpRequest, httpResponse);
                return;
            }

            resolveStaticRequest(httpRequest, httpResponse);
        } catch (final IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void initSessionIfNecessary(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (httpRequest.isSessionEmpty()) {
            final HttpSession session = httpRequest.getSession();
            httpResponse.setSession(session.getId());
        }
    }

    private void resolveStaticRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        final HttpPath path = httpRequest.getPath();
        httpResponse.setContentType(ContentType.from(path));
        httpResponse.forward(path.getPath());
    }

}
