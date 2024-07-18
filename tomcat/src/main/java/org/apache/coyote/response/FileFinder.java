package org.apache.coyote.response;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileFinder {

    public static String find(String filePath) {
        ClassLoader classLoader = FileFinder.class.getClassLoader();
        URL resource = classLoader.getResource("static" + filePath);
        Path path = Path.of(Objects.requireNonNull(resource).getPath());
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + filePath);
        }
    }
}
