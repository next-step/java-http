package org.apache.util;

import java.util.Map;

public class MimeTypes {
    private final Map<String, String> extensionMimeTypeMap = Map.of(
            ".html", "text/html",
            ".js", "application/javascript",
            ".css", "text/css",
            ".svg", "image/svg+xml");

    public String guessByExtension(String extension) {
        return extensionMimeTypeMap.get(extension);
    }
}
