package camp.nextstep.response;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestCookie;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.catalina.Session;
import org.apache.util.MimeTypes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final OutputStream outputStream;
    private final HttpRequest request;
    private final Map<String, String> headersMap;
    private final StaticResourceLoader staticResourceLoader;

    public HttpResponse(OutputStream outputStream, HttpRequest request, StaticResourceLoader staticResourceLoader) {
        this.outputStream = outputStream;
        this.request = request;
        this.headersMap = new HashMap<>();
        this.staticResourceLoader = staticResourceLoader;
    }

    public void setHeader(String key, String value) {
        this.headersMap.put(key, value);
    }

    public void render(String requestPath) throws IOException {
        render(ResponseStatusCode.OK, requestPath);
    }

    public void render(ResponseStatusCode statusCode, String requestPath) throws IOException {
        assert requestPath.startsWith("/");

        String content = staticResourceLoader.readAllLines("static" + requestPath);
        String contentType = guessMimeTypeFromPath(requestPath);

        setHeader("Content-Type", contentType + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(content.getBytes().length));

        writeResponse(statusCode, content);
    }

    public void renderText(String text, String contentType) throws IOException {
        renderText(ResponseStatusCode.OK, text, contentType);
    }

    private void renderText(ResponseStatusCode statusCode, String text, String contentType) throws IOException {
        setHeader("Content-Type", contentType + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(text.getBytes().length));

        writeResponse(statusCode, text);
    }

    public void redirectTo(String redirectTo) throws IOException {
        setHeader("Location", redirectTo);

        writeResponse(ResponseStatusCode.Found, null);
    }

    // ---------------------------------------------------------------------------------------------------------------

    private void writeResponse(ResponseStatusCode statusCode, String content) throws IOException {
        ResponseWriter writer = new ResponseWriter(outputStream);

        writer.setResponseLine(statusCode);
        writer.appendHeaders(headersMap);
        if (needToUpdateSessionId(request)) {
            writer.setHeader("Set-Cookie",
                    HttpRequestCookie.JSESSIONID_NAME + "=" + request.getSession().getId() + "; Path=/");
        }

        if (content != null) {
            writer.setContent(content);
        }

        writer.write();
    }

    private boolean needToUpdateSessionId(HttpRequest request) throws IOException {
        HttpRequestCookie sessionCookie = request.getCookies().get(HttpRequestCookie.JSESSIONID_NAME);
        if (sessionCookie == null) return true;

        Session session = request.getSession();
        return !session.getId().equals(sessionCookie.getValue());
    }

    private String guessMimeTypeFromPath(String path) {
        final String FALLBACK_MIME_TYPE = "text/html";

        if (path.equals("/")) return "text/html";

        String extension = extractExtensionFromPath(path);
        if (extension == null) return FALLBACK_MIME_TYPE;

        String mimeType = MimeTypes.guessByExtension(extension);
        if (mimeType == null) return FALLBACK_MIME_TYPE;

        return mimeType;
    }

    private String extractExtensionFromPath(String path) {
        int lastPeriod = path.lastIndexOf(".");
        if (lastPeriod < 0) return null;

        return path.substring(lastPeriod);
    }
}
