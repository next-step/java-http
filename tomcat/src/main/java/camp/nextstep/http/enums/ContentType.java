package camp.nextstep.http.enums;

public enum ContentType {
    TEXT_HTML("text/html", ""),
    TEXT_CSS("text/css", "css");

    private String contentTypeHeader;
    private String fileExt;

    public String getContentTypeHeader() {
        return contentTypeHeader;
    }

    public String getFileExt() {
        return fileExt;
    }

    ContentType(String contentTypeHeader, String fileExt) {
        this.contentTypeHeader = contentTypeHeader;
        this.fileExt = fileExt;
    }

    public static ContentType findContentTypeByFileExt(String fileExt) {
        for (ContentType contentType : ContentType.values()) {
            if (contentType.fileExt.equals(fileExt)) {
                return contentType;
            }
        }
        return TEXT_HTML;
    }
}
