package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import java.io.File;
import java.net.URL;

import org.apache.coyote.Processor;
import org.apache.coyote.support.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;
import java.util.StringJoiner;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String INDEX_PATH = "/index.html";
    private static final String REGISTER_PATH = "/register.html";
    public static final String UNAUTHORIZED_PATH = "/401.html";

    private final Socket connection;
    private final RequestLineParser requestLineParser = new RequestLineParser();

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
            final HttpRequest httpRequest = requestLineParser.parse(httpRequestMessage);

            final var response = createResponse(httpRequest);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String createResponse(HttpRequest httpRequest) throws IOException {
        if (httpRequest.getHttpPath().equals("/")) {
            return defaultResponse();
        }else if (httpRequest.getHttpPath().equals("/login")) {
            return loginResponse(httpRequest);
        }else if (httpRequest.getHttpPath().equals("/register")) {
            return registerResponse(httpRequest);
        }

        return staticResponse(httpRequest);
    }

    private String registerResponse(HttpRequest request) throws IOException {
        return switch(request.getHttpMethod()) {
            case GET -> {
                final URL resource = ResourceFinder.findResource(REGISTER_PATH);
                final String content = ResourceFinder.findContent(resource);

                yield  String.join("\r\n",
                        "HTTP/1.1 200 OK ",
                        "Content-Type: "+ ContentType.TEXT_HTML.getType() +";charset=utf-8 ",
                        "Content-Length: " + content.getBytes().length + " ",
                        "",
                        content);
            }
            case POST -> {
                registerUser(request);
                yield redirectResponse();
            }
        };
    }

    private void registerUser(HttpRequest request) {
        QueryParamsMap queryParamsMap = request.getRequestTarget().queryParamsMap();
        final String account = queryParamsMap.value().get("account");
        final String email = queryParamsMap.value().get("email");
        final String password = queryParamsMap.value().get("password");

        InMemoryUserRepository.save(new User(account, email, password));
    }

    private String staticResponse(HttpRequest httpRequest) throws IOException {
        final File file = ResourceFinder.findFile(httpRequest.getHttpPath());
        final URL resource = ResourceFinder.findResource(httpRequest.getHttpPath());
        final String extension = FileUtils.extractExtension(file.getPath());
        final ContentType contentType = ContentType.fromExtension(extension);
        final String content = ResourceFinder.findContent(resource);

        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: " + contentType.getType() + ";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                "",
                content);
    }

    private String loginResponse(HttpRequest request) throws IOException {
        QueryParamsMap queryParamsMap = request.getRequestTarget().queryParamsMap();
        if(queryParamsMap == null) {
            return staticResponse(request);
        }

        final String account = queryParamsMap.value().get("account");
        final String password = queryParamsMap.value().get("password");
        Optional<User> userOptional = InMemoryUserRepository.findByAccount(account);
        if(userOptional.isEmpty() || !userOptional.get().checkPassword(password)) {
            return unauthorizedResponse();
        }

        log.info(userOptional.get().toString());
        return redirectResponse();
    }

    private String redirectResponse() throws IOException {
        final URL resource = ResourceFinder.findResource(INDEX_PATH);
        final String content = ResourceFinder.findContent(resource);

        return String.join("\r\n",
                "HTTP/1.1 302 OK ",
                "Content-Type: "+ ContentType.TEXT_HTML.getType() +";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                "",
                content);
    }

    private String unauthorizedResponse() throws IOException {
        final URL resource = ResourceFinder.findResource(UNAUTHORIZED_PATH);
        final String content = ResourceFinder.findContent(resource);

        return String.join("\r\n",
                "HTTP/1.1 401 OK ",
                "Content-Type: "+ ContentType.TEXT_HTML.getType() +";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                "",
                content);
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
