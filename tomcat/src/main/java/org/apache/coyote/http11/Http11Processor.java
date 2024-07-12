package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import java.io.File;
import java.net.URL;
import java.util.Map;
import org.apache.coyote.Processor;
import org.apache.coyote.support.FileUtils;
import org.apache.exception.NotFoundUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final RequestLineParser requestLineParser = new RequestLineParser();
    private final ResourceFinder resourceFinder = new ResourceFinder();

    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";

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

            final String httpRequestMessage = readHttpRequestMessage(br);
            final HttpServletRequest httpServletRequest = requestLineParser.parse(httpRequestMessage);

            final var response = createResponse(httpServletRequest);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String createResponse(HttpServletRequest httpServletRequest) throws IOException {
        if (httpServletRequest.httpPath().equals(ROOT_PATH)) {
            return defaultResponse();
        }

        if(httpServletRequest.httpPath().equals(LOGIN_PATH)) {
            validateUser(httpServletRequest);
        }

        final File file = resourceFinder.findFile(httpServletRequest.httpPath());
        final URL resource = resourceFinder.findResource(httpServletRequest.httpPath());
        final String extension = FileUtils.extractExtension(file.getPath());
        final ContentType contentType = ContentType.fromExtension(extension);
        final String content = resourceFinder.findContent(resource);

        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: " + contentType.getType() + ";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                "",
                content);
    }

    private static void validateUser(HttpServletRequest httpServletRequest) {
        final Map<String, String> queryParamMap = httpServletRequest.requestTarget().queryParamsMap().value();
        final String account = queryParamMap.get("account");
        final String password = queryParamMap.get("password");
        final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NotFoundUserException::new);
        user.checkPassword(password);
        System.out.println(user);
    }

    private String defaultResponse() {
        final String content = "Hello world!";

        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: "+ ContentType.TEXT_HTML.getType() +";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                "",
                content);
    }

    private String readHttpRequestMessage(final BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder("\n");
        while (true) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            sb.append(line);
        }
        return sb.toString();
    }

}
