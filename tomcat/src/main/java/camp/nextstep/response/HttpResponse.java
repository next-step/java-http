package camp.nextstep.response;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestCookie;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.coyote.http11.SessionManager;
import org.apache.util.MimeTypes;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private static final MimeTypes mimeTypes = new MimeTypes();

    private final HttpRequest request;
    private final SessionManager sessionManager;
    private final OutputStream outputStream;
    private final StaticResourceLoader staticResourceLoader;

    public HttpResponse(HttpRequest request, SessionManager sessionManager, OutputStream outputStream) {
        this.request = request;
        this.sessionManager = sessionManager;
        this.outputStream = outputStream;
        this.staticResourceLoader = new StaticResourceLoader();
    }

    public void renderStaticResource(String staticFilePath) throws IOException {
        assert staticFilePath.startsWith("/");

        final String content = staticResourceLoader.readAllLines("static" + staticFilePath);
        final String mimeType = guessMimeTypeFromPath(staticFilePath);

        render("200 OK", content, mimeType);
    }

    public void render404() throws IOException {
        final String content = staticResourceLoader.readAllLines("static/404.html");

        render("404 Not Found", content, "text/html");
    }

    public void redirectTo(String redirectTo) throws IOException {
        String responseStatus = "302 Found";
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("HTTP/1.1 ").append(responseStatus).append(" ").append("\r\n");
        responseBuilder.append("Location: ").append(redirectTo).append(" ").append("\r\n");
        if (needToUpdateSessionId(request)) {
            responseBuilder.append("Set-Cookie: ")
                    .append(HttpRequestCookie.JSESSIONID_NAME)
                    .append("=")
                    .append(request.getSession(sessionManager, true).getId())
                    .append("; Path=/ ")
                    .append("\r\n");
        }
        outputStream.write(responseBuilder.toString().getBytes());
        outputStream.flush();
    }

    public void renderText(String content) throws IOException {
        render("200 OK", content, "text/html");
    }

    // ----------------------------------------------------------------------

    private boolean needToUpdateSessionId(HttpRequest request) throws IOException {
        String oldId = request.getSessionIdFromCookie();
        if (oldId == null) return true;

        String newId = request.getSession(sessionManager, true).getId();
        return !oldId.equals(newId);
    }

    private void render(String responseStatus,
                        String content,
                        String mimeType) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("HTTP/1.1 ").append(responseStatus).append(" ").append("\r\n");
        responseBuilder.append("Content-Type: ").append(mimeType).append(";charset=utf-8 ").append("\r\n");
        responseBuilder.append("Content-Length: ").append(content.getBytes().length).append(" ").append("\r\n");
        if (needToUpdateSessionId(request)) {
            responseBuilder.append("Set-Cookie: ")
                    .append(HttpRequestCookie.JSESSIONID_NAME)
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
