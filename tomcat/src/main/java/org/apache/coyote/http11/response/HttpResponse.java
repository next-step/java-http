package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.model.ContentType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {
    private static final String PATH_PREFIX = "static";

    private final ResponseHeaders headers;
    private final OutputStream outputStream;

    private HttpResponse(final OutputStream outputStream) {
        this.headers = new ResponseHeaders();
        this.outputStream = outputStream;
    }

    public static HttpResponse of(final OutputStream outputStream) {
        return new HttpResponse(outputStream);
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public void setContentType(final ContentType contentType) {

    }

    public void forwardBody(final String responseBody) throws IOException {
        headers.setContentType(ContentType.TEXT_HTML);
        headers.setContentLength(responseBody.getBytes().length);
        final var response = String.join(System.lineSeparator(),
                StatusLine.createOk().convertToString(),
                headers.convertToString(),
                "",
                responseBody);

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    public void forward(final String path) throws IOException {
        final Resource resource = getResource(path);
        final byte[] resourceBytes = resource.readAllBytes();
        final String responseBody = new String(resourceBytes);
        headers.setContentLength(resourceBytes.length);
        final var response = String.join(System.lineSeparator(),
                StatusLine.createOk().convertToString(),
                headers.convertToString(),
                "",
                responseBody);

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    private Resource getResource(final String path) {
        final var resource = new Resource(PATH_PREFIX + path);
        if (resource.exists()) {
            return resource;
        }
        return new Resource(PATH_PREFIX + "/404.html");
    }

    public void sendRedirect(final String location) throws IOException {
        headers.setLocation(location);
        final var response = String.join(System.lineSeparator(),
                StatusLine.createFound().convertToString(),
                headers.convertToString(),
                "",
                "");

        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}
