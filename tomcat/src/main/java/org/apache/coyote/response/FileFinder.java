package org.apache.coyote.response;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileFinder {

    public static String find(String filePath) throws IOException {
        ClassLoader classLoader = FileFinder.class.getClassLoader();
        URL resource = classLoader.getResource("static" + filePath);
        Path path = Path.of(Objects.requireNonNull(resource).getPath());
        return new String(Files.readAllBytes(path));
    }
}
