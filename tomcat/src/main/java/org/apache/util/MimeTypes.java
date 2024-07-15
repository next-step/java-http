package org.apache.util;

import java.util.HashMap;
import java.util.Map;

public enum MimeTypes {
    HTML(".html", "text/html"),
    JS(".js", "application/javascript"),
    CSS(".css", "text/css"),
    SVG(".svg", "image/svg+xml"),
    ;

    private static final Map<String, String> extensionMimeTypeMap = buildMimeTypeMap();
    private final String extension;
    private final String mimeType;

    MimeTypes(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static String guessByExtension(String extension) {
        return extensionMimeTypeMap.get(extension);
    }

    private static Map<String, String> buildMimeTypeMap() {
        Map<String, String> extensionMimeTypeMap = new HashMap<>();

        for (MimeTypes eachMimeType : values()) {
            extensionMimeTypeMap.put(eachMimeType.extension, eachMimeType.mimeType);
        }

        return extensionMimeTypeMap;
    }
}
