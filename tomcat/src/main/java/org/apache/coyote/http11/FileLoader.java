package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Pattern;

public final class FileLoader {

    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile(".+\\.html");

    private FileLoader() {
    }

    public static Path read(String resourceName) throws IOException {
        validateFileScheme(resourceName);

        URL resource = FileLoader.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }

        return new File(resource.getFile()).toPath();
    }

    private static void validateFileScheme(String resourceName) {
        if (!FILE_EXTENSION_PATTERN.matcher(resourceName).matches()) {
            throw new IllegalArgumentException("Invalid file extension: " + resourceName);
        }
    }


}
