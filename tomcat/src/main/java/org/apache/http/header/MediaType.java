package org.apache.http.header;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MediaType {
    public static final MediaType TEXT_HTML = new MediaType("text/html");
    public static final MediaType TEXT_CSS = new MediaType("text/css");

    private final String contentType;

    private MediaType(String contentType) {
        this.contentType = contentType;
    }

    public MediaType(Path path) throws IOException {
        this.contentType = Files.probeContentType(path);
    }

    @Override
    public String toString() {
        return contentType;
    }
}
