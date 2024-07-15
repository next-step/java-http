package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Pattern;

public final class FileLoader {

    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile(".+\\.("+ ContentType.extensionPattern() +")$");

    private FileLoader() {
    }

    public static byte[] read(String resourceName) throws IOException {
        if (!isStaticResource(resourceName)) {
            throw new IllegalArgumentException("Invalid file extension: " + resourceName);
        }

        URL resource = FileLoader.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }

        return Files.readAllBytes(new File(resource.getFile()).toPath());
    }

    public static boolean isStaticResource(String resourceName) {
        return FILE_EXTENSION_PATTERN.matcher(resourceName).matches();
    }
}