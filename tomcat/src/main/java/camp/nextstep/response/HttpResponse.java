package camp.nextstep.response;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestCookie;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.util.MimeTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final HttpRequest request;
    private final StaticResourceLoader staticResourceLoader;

    private final Map<String, String> headersMap;
    private final List<HttpRequestCookie> newCookies;
    private ResponseStatusCode statusCode;
    private String content;

    public HttpResponse(HttpRequest request, StaticResourceLoader staticResourceLoader) {
        this.request = request;
        this.staticResourceLoader = staticResourceLoader;

        this.headersMap = new HashMap<>();
        this.newCookies = new ArrayList<>();
        this.statusCode = null;
        this.content = null;
    }

    public void setSessionCookie() throws IOException {
        if (request.hasSession()) return;

        String sessionId = request.getSession().getId();
        setCookie(new HttpRequestCookie(HttpRequestCookie.JSESSIONID_NAME, sessionId));
    }

    public void setHeader(String key, String value) {
        headersMap.put(key, value);
    }

    public void setCookie(HttpRequestCookie cookie) {
        newCookies.add(cookie);
    }

    public void render(String requestPath) throws IOException {
        render(ResponseStatusCode.OK, requestPath);
    }

    public void render(ResponseStatusCode statusCode, String requestPath) throws IOException {
        assert requestPath.startsWith("/");

        this.statusCode = statusCode;

        String content = readFile("static" + requestPath);
        String contentType = guessMimeTypeFromPath(requestPath);
        setContent(content, contentType);
    }

    public void renderText(String text, String contentType) {
        renderText(ResponseStatusCode.OK, text, contentType);
    }

    private void renderText(@SuppressWarnings("SameParameterValue") ResponseStatusCode statusCode, String text, String contentType) {
        this.statusCode = statusCode;
        setContent(text, contentType);
    }

    private void setContent(String content, String contentType) {
        setHeader("Content-Type", contentType + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(content.getBytes().length));
        this.content = content;
    }

    public void redirectTo(String redirectTo) {
        this.statusCode = ResponseStatusCode.Found;
        setHeader("Location", redirectTo);
    }

    public HttpRequest getRequest() {
        return request;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public List<HttpRequestCookie> getNewCookies() {
        return newCookies;
    }

    public ResponseStatusCode getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

    private String readFile(String path) throws IOException {
        return staticResourceLoader.readAllLines(path);
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
