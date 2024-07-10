package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.RequestNotFoundException;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import camp.nextstep.request.Cookie;
import camp.nextstep.request.QueryParameters;
import camp.nextstep.request.Request;
import camp.nextstep.request.RequestParser;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.catalina.Session;
import org.apache.coyote.Processor;
import org.apache.util.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final SessionManager sessionManager;

    private final RequestParser requestParser;
    private final StaticResourceLoader staticResourceLoader;
    private final MimeTypes mimeTypes;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
        this.sessionManager = SessionManager.INSTANCE;

        this.requestParser = new RequestParser();
        this.staticResourceLoader = new StaticResourceLoader();
        this.mimeTypes = new MimeTypes();
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
             final var outputStream = connection.getOutputStream()
        ) {
            Request request = requestParser.parse(bufferedReader);
            try {
                processRequest(request, outputStream);
            } catch (RequestNotFoundException e) {
                render404(request, outputStream);
                throw e;
            }
        } catch (IOException | UncheckedServletException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processRequest(Request request, OutputStream outputStream) throws IOException, URISyntaxException {
        String requestPath = request.getPath();

        if (requestPath.equals("/")) {
            processHelloWorld(request, outputStream);
            return;
        }

        if (requestPath.equals("/login") && request.isGET()) {
            processGetLogin(request, outputStream);
            return;
        }

        if (requestPath.equals("/login") && request.isPOST()) {
            processPostLogin(request, outputStream);
            return;
        }

        if (requestPath.equals("/register") && request.isGET()) {
            processGetRegister(request, outputStream);
            return;
        }

        if (requestPath.equals("/register") && request.isPOST()) {
            processPostRegister(request, outputStream);
            return;
        }

        processRenderStaticPage(request, outputStream);
    }

    private void processHelloWorld(Request request, OutputStream outputStream) throws IOException {
        render("200 OK", "Hello world!", "text/html", request, outputStream);
    }

    private void processGetLogin(Request request, OutputStream outputStream) throws IOException {
        if (isLoggedIn(request)) {
            redirectTo("/index.html", request, outputStream);
            return;
        }
        renderStaticResource("/login.html", request, outputStream);
    }

    private boolean isLoggedIn(Request request) throws IOException {
        Session session = request.getSession(sessionManager, false);
        if (session == null) return false;

        return session.getAttribute("user") != null;
    }

    private void processPostLogin(Request request, OutputStream outputStream) throws IOException {
        QueryParameters requestBody = request.getRequestBody();
        String account = requireNonNull(requestBody.getString("account"));
        String password = requireNonNull(requestBody.getString("password"));

        Optional<User> user = InMemoryUserRepository.findByAccount(account);
        Boolean found = user
                .map(acc -> acc.checkPassword(password))
                .orElse(false);
        if (!found) {
            redirectTo("/401.html", request, outputStream);
            return;
        }

        signInAs(user.get(), request);

        redirectTo("/index.html", request, outputStream);
    }

    private void signInAs(User user, Request request) throws IOException {
        final var session = request.getSession(sessionManager, true);
        session.setAttribute("user", user);
    }

    private void processGetRegister(Request request, OutputStream outputStream) throws IOException {
        renderStaticResource("/register.html", request, outputStream);
    }

    private void processPostRegister(Request request, OutputStream outputStream) throws IOException {
        final QueryParameters requestBody = request.getRequestBody();

        final String account = requireNonNull(requestBody.getString("account"));
        final String email = requireNonNull(requestBody.getString("email"));
        final String password = requireNonNull(requestBody.getString("password"));

        InMemoryUserRepository.save(new User(account, password, email));

        redirectTo("/index.html", request, outputStream);
    }

    private void processRenderStaticPage(Request request, OutputStream outputStream) throws IOException {
        renderStaticResource(request.getPath(), request, outputStream);
    }

    private void renderStaticResource(String staticFilePath, Request request, OutputStream outputStream) throws IOException {
        final String content = staticResourceLoader.readAllLines("static" + staticFilePath);
        final String mimeType = guessMimeTypeFromPath(staticFilePath);

        render("200 OK", content, mimeType, request, outputStream);
    }

    private void render404(Request request, OutputStream outputStream) throws IOException {
        final String content = staticResourceLoader.readAllLines("static/404.html");

        render("404 Not Found", content, "text/html", request, outputStream);
    }

    private void redirectTo(String redirectTo, Request request, OutputStream outputStream) throws IOException {
        String responseStatus = "302 Found";
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("HTTP/1.1 ").append(responseStatus).append(" ").append("\r\n");
        responseBuilder.append("Location: ").append(redirectTo).append(" ").append("\r\n");
        if (needToUpdateSessionId(request)) {
            responseBuilder.append("Set-Cookie: ")
                    .append(Cookie.JSESSIONID_NAME)
                    .append("=")
                    .append(request.getSession(sessionManager, true).getId())
                    .append("; Path=/ ")
                    .append("\r\n");
        }
        outputStream.write(responseBuilder.toString().getBytes());
        outputStream.flush();
    }

    private boolean needToUpdateSessionId(Request request) throws IOException {
        String oldId = request.getSessionIdFromCookie();
        if (oldId == null) return true;

        String newId = request.getSession(sessionManager, true).getId();
        return !oldId.equals(newId);
    }

    private void render(String responseStatus,
                        String content,
                        String mimeType,
                        Request request, OutputStream outputStream) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("HTTP/1.1 ").append(responseStatus).append(" ").append("\r\n");
        responseBuilder.append("Content-Type: ").append(mimeType).append(";charset=utf-8 ").append("\r\n");
        responseBuilder.append("Content-Length: ").append(content.getBytes().length).append(" ").append("\r\n");
        if (needToUpdateSessionId(request)) {
            responseBuilder.append("Set-Cookie: ")
                    .append(Cookie.JSESSIONID_NAME)
                    .append("=")
                    .append(request.getSession(sessionManager, true).getId())
                    .append("; Path=/ ")
                    .append("\r\n");
        }
        responseBuilder.append("\r\n").append(content);

        outputStream.write(responseBuilder.toString().getBytes());
        outputStream.flush();
    }

    private String guessMimeTypeFromPath(String path) {
        final String FALLBACK_MIME_TYPE = "text/html";

        if (path.equals("/")) return "text/html";

        String extension = extractExtensionFromPath(path);
        if (extension == null) return FALLBACK_MIME_TYPE;

        String mimeType = mimeTypes.guessByExtension(extension);
        if (mimeType == null) return FALLBACK_MIME_TYPE;

        return mimeType;
    }

    private String extractExtensionFromPath(String path) {
        int lastPeriod = path.lastIndexOf(".");
        if (lastPeriod < 0) return null;

        return path.substring(lastPeriod);
    }
}
