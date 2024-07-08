package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.ContentType;

public class UrlPath {
    private static final String ROOT_PATH = "/";
    private static final String EXTENSION_DELIMITER = ".";

    private final String urlPath;

    public UrlPath(final String urlPath) {
        this.urlPath = urlPath;
    }

    public String urlPath() {
        if (hasNotExtension()) {
            return urlPath + ContentType.TEXT_HTML.extension();
        }

        return urlPath;
    }

    public boolean isRootPath() {
        return ROOT_PATH.equals(urlPath);
    }

    public ContentType findContentType() {
        if (isRootPath() || hasNotExtension()) {
            return ContentType.TEXT_HTML;
        }

        final String extension = urlPath.substring(urlPath.lastIndexOf(EXTENSION_DELIMITER));
        return ContentType.findByExtension(extension);
    }

    private boolean hasNotExtension() {
        return !urlPath.contains(EXTENSION_DELIMITER);
    }
}
