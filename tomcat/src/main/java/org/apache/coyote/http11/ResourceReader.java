package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class ResourceReader {
    private static final String BASE_DIRECTORY = "static";
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String HTML_FILE_EXTENSION = ".html";

    public File readFile(final String path) {
        URL resourceUrl = findResourceUrl(path);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }

        return new File(resourceUrl.getFile());
    }

    public String read(final String path) {
        URL resourceUrl = findResourceUrl(path);
        return findFileFrom(resourceUrl);
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

    private String findFileFrom(final URL resourceUrl) {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }

        try {
            return new String(Files.readAllBytes(new File(resourceUrl.getFile()).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}