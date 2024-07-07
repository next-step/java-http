package org.apache.coyote.response;

import java.util.Arrays;

public enum MimeType {
    HTML(".html", "text/html"),
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
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 타입입니다."));
    }

    public String getContentType() {
        return contentType;
    }
}
