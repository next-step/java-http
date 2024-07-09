package camp.nextstep.http.domain;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private static final String PATH_PREFIX = "static";
    private static final String NOT_FOUND_PATH = "/404.html";

    private final OutputStream outputStream;
    private final HttpHeaders headers;

    public HttpResponse(final OutputStream outputStream) {
        this.outputStream = new BufferedOutputStream(outputStream);
        this.headers = new HttpHeaders();
    }

    public void addHeader(final String name, final String value) {
        headers.add(name, value);
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

    public void forwardBody(final String responseBody) throws IOException {
        headers.setContentType(ContentType.HTML);
        headers.setContentLength(responseBody.getBytes().length);
        final var response = String.join(System.lineSeparator(),
                StatusLine.createOk().convertToString(),
                headers.convertToString(),
                "",
                responseBody);

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    public void sendRedirect(final String location) throws IOException {
        headers.setLocation(location);
        final var response = String.join(System.lineSeparator(),
                StatusLine.createFound().convertToString(),
                headers.convertToString(),
                "");

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    private Resource getResource(final String path) {
        final Resource resource = new Resource(PATH_PREFIX + path);
        if (resource.exists()) {
            return resource;
        }
        return new Resource(PATH_PREFIX + NOT_FOUND_PATH);
    }
}
