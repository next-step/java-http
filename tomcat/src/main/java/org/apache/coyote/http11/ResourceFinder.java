package org.apache.coyote.http11;

import java.io.File;
import java.net.URL;

public class ResourceFinder {
    private static final String BASE_DIRECTORY = "static";
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String HTML_FILE_EXTENSION = ".html";

    public File findByPath(final String path) {
        URL resourceUrl = findResourceUrl(path);
        validate(resourceUrl);
        return new File(resourceUrl.getFile());
    }

    private URL findResourceUrl(final String path) {
        String fileExtension = ensureFileExtension(path);
        String resourcePath = getResourcePath(path, fileExtension);

        return getClass()
                .getClassLoader()
                .getResource(BASE_DIRECTORY + resourcePath);
    }

    private String ensureFileExtension(final String resource) {
        if (!resource.contains(FILE_EXTENSION_SEPARATOR)) {
            return HTML_FILE_EXTENSION;
        }

        return "";
    }

    private String getResourcePath(final String path, final String fileExtension) {
        if (isPathIndex(path)) {
            return "/index.html";
        }

        return path + fileExtension;
    }

    private boolean isPathIndex(final String path) {
        return path.equals("/");
    }

    private void validate(final URL resourceUrl) {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }
    }
}