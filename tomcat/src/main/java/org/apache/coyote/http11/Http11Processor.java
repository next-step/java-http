package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import java.io.File;
import java.net.URL;

import org.apache.catalina.manager.SessionManager;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.model.*;
import org.apache.coyote.support.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String INDEX_PATH = "/index.html";
    private static final String REGISTER_PATH = "/register.html";
    public static final String UNAUTHORIZED_PATH = "/401.html";

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
                yield redirectResponse(request.getCookie());
            }
        };
    }

    private void registerUser(final HttpRequest request) {
        final String account = request.getBody().get("account");
        final String email = request.getBody().get("email");
        final String password = request.getBody().get("password");

        InMemoryUserRepository.save(new User(account, email, password));
    }

    private String staticResponse(final HttpRequest httpRequest) throws IOException {
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
        return switch(request.getHttpMethod()) {
            case GET -> {
                if(request.getCookie().hasJSessionId() && existSession(request)) {
                    yield redirectResponse(request.getCookie());
                }

                yield staticResponse(request);
            }
            case POST -> {
                final String account = request.getBody().get("account");
                final String password = request.getBody().get("password");
                Optional<User> userOptional = InMemoryUserRepository.findByAccount(account);
                if(userOptional.isEmpty() || !userOptional.get().checkPassword(password)) {
                    yield  unauthorizedResponse();
                }

                log.info(userOptional.get().toString());

                /* 세션 설정 */
                final String key = UUID.randomUUID().toString();
                SessionManager.getInstance().add(new HttpSession(key));
                HttpSession session = SessionManager.getInstance().findSession(key);
                session.setAttribute("user", userOptional.get());

                yield redirectResponse(request.getCookie());
            }
        };
    }

    private static boolean existSession(HttpRequest request) {
        final String key = request.getCookie().JSessionId();
        final HttpSession session = SessionManager.getInstance().findSession(key);

        return session == null;
    }

    private String redirectResponse(HttpCookie cookie) throws IOException {
        final URL resource = ResourceFinder.findResource(INDEX_PATH);
        final String content = ResourceFinder.findContent(resource);

        return String.join("\r\n",
                "HTTP/1.1 302 OK ",
                "Content-Type: "+ ContentType.TEXT_HTML.getType() +";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                !cookie.hasJSessionId() ? "Set-Cookie: " + UUID.randomUUID() + " " : "",
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
