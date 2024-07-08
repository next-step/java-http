package org.apache.coyote.http11;

import org.apache.coyote.HeaderMapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Response {

    private final HeaderMapping headerMapping = new HeaderMapping();

    private final StringBuilder body = new StringBuilder();

    public void init() {
        this.headerMapping.addHeader("Content-Type", "text/plain;charset=utf-8");
    }

    public void setStaticResource(final String filePath) throws IOException {
        final URL resource = getClass().getClassLoader().getResource("static/" + filePath);
        final File file = new File(resource.getFile());

        if (!file.isFile()) {
            notFound();

            return;
        }

        final Path path = file.toPath();
        final String responseBody = new String(Files.readAllBytes(path));
        final String mimeType = Files.probeContentType(path);

        setBody(responseBody, mimeType);
    }

    public void setBody(final String body) {
        setBody(body, "text/plain");
    }

    public void setBody(final String body, final String mimeType) {
        this.body.setLength(0);
        this.body.append(body);
        this.headerMapping.addHeader("Content-Type", mimeType + ";charset=utf-8");
        this.headerMapping.addHeader("Content-Length", Integer.toString(this.body.length()));
    }

    public void notFound() {
        setBody("Hello world!", "text/html");
    }

    public byte[] toBytes() {
        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                headerMapping.convertHttpHeaders(),
                "",
                body.toString()).getBytes();
    }
}
