package org.apache.coyote.response;

import java.util.Arrays;

public enum MimeType {
    HTML(".html", "text/html;charset=utf-8"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    ;

    private final String fileExtension;
    private final String contentType;

    MimeType(String fileExtension, String contentType) {
        this.fileExtension = fileExtension;
        this.contentType = contentType;
    }

    public static MimeType from(String path) {
        return Arrays.stream(values())
                .filter(mimeType -> path.contains(mimeType.fileExtension))
                .findFirst()
                .orElse(HTML);
    }

    public static MimeType parseMimeType(String httpPath) {
        return null;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
