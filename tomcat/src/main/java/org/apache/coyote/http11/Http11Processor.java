package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.RequestNotFoundException;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import camp.nextstep.request.QueryParameters;
import camp.nextstep.request.Request;
import camp.nextstep.request.RequestParser;
import camp.nextstep.staticresource.StaticResourceLoader;
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

import static java.util.Objects.requireNonNull;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final RequestParser requestParser;
    private final StaticResourceLoader staticResourceLoader;
    private final MimeTypes mimeTypes;

    public Http11Processor(final Socket connection) {
        this.connection = connection;

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
            try {
                Request request = requestParser.parse(bufferedReader);

                processRequest(request.getPath(), outputStream, request);
            } catch (RequestNotFoundException e) {
                handle404(outputStream);
                throw e;
            }
        } catch (IOException | UncheckedServletException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processRequest(String requestPath, OutputStream outputStream, Request request) throws IOException, URISyntaxException {
        if (requestPath.isEmpty() || requestPath.equals("/")) {
            processHelloWorld(outputStream);
            return;
        }

        if (requestPath.equals("/login") && request.isGET()) {
            processGetLogin(outputStream);
            return;
        }

        if (requestPath.equals("/login") && request.isPOST()) {
            processPostLogin(outputStream, request.getRequestBody());
            return;
        }

        if (requestPath.equals("/register") && request.isGET()) {
            processGetRegister(outputStream);
            return;
        }

        if (requestPath.equals("/register") && request.isPOST()) {
            processPostRegister(outputStream, request.getRequestBody());
            return;
        }

        processRenderStaticPage(outputStream, requestPath);
    }

    private void processHelloWorld(OutputStream outputStream) throws IOException {
        handlePlainText(outputStream, "Hello world!");
    }

    private void processGetLogin(OutputStream outputStream) throws IOException {
        handleRenderStaticResource(outputStream, "/login.html");
    }

    private void processPostLogin(OutputStream outputStream, QueryParameters requestBody) throws IOException {
        String account = requireNonNull(requestBody.getString("account"));
        String password = requireNonNull(requestBody.getString("password"));

        Boolean found = InMemoryUserRepository.findByAccount(account)
                .map(acc -> acc.checkPassword(password))
                .orElse(false);
        if (!found) {
            handle302RedirectTo("/401.html", outputStream);
            return;
        }

        handle302RedirectTo("/index.html", outputStream);
    }

    private void processGetRegister(OutputStream outputStream) throws IOException {
        handleRenderStaticResource(outputStream, "/register.html");
    }

    private void processPostRegister(OutputStream outputStream, QueryParameters requestBody) throws IOException {
        final String account = requireNonNull(requestBody.getString("account"));
        final String email = requireNonNull(requestBody.getString("email"));
        final String password = requireNonNull(requestBody.getString("password"));

        InMemoryUserRepository.save(new User(account, password, email));

        handle302RedirectTo("/index.html", outputStream);
    }

    private void processRenderStaticPage(OutputStream outputStream, String requestPath) throws IOException {
        handleRenderStaticResource(outputStream, requestPath);
    }

    private void handlePlainText(OutputStream outputStream, String content) throws IOException {
        render("200 OK", content, "text/html", outputStream);
    }

    private void handleRenderStaticResource(OutputStream outputStream, String staticFilePath) throws IOException {
        final String content = staticResourceLoader.readAllLines("static" + staticFilePath);
        final String mimeType = guessMimeTypeFromPath(staticFilePath);

        render("200 OK", content, mimeType, outputStream);
    }

    private void handle404(OutputStream outputStream) throws IOException {
        final String content = staticResourceLoader.readAllLines("static/404.html");

        render("404 Not Found", content, "text/html", outputStream);
    }

    private void handle302RedirectTo(String redirectTo, OutputStream outputStream) throws IOException {
        String responseStatus = "302 Found";
        final String response = String.join("\r\n",
                "HTTP/1.1 " + responseStatus + " ",
                "Location: " + redirectTo + " ");

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    private void render(String responseStatus, String content, String mimeType, OutputStream outputStream) throws IOException {
        final String response = String.join("\r\n",
                "HTTP/1.1 " + responseStatus + " ",
                "Content-Type: " + mimeType + ";charset=utf-8 ",
                "Content-Length: " + content.getBytes().length + " ",
                "",
                content);

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    private String guessMimeTypeFromPath(String path) {
        final String FALLBACK_MIME_TYPE = "text/html";

        if (path.equals("/")) {
            return "text/html";
        }

        String extension = extractExtensionFromPath(path);
        if (extension == null) return FALLBACK_MIME_TYPE;

        String mimeType = mimeTypes.guessByExtension(extension);
        if (mimeType == null) return FALLBACK_MIME_TYPE;

        return mimeType;
    }

    private String extractExtensionFromPath(String path) {
        int lastPeriod = path.lastIndexOf(".");
        if (lastPeriod < 0) {
            return null;
        }
        return path.substring(lastPeriod);
    }
}
