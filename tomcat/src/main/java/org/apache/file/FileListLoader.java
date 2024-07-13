package org.apache.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class FileListLoader {
    private final String prefix;

    public FileListLoader(String prefix) {
        this.prefix = getClass().getClassLoader().getResource(prefix).getPath();
    }

    public Map<String, Path> load() {
        try (var list = Files.walk(Paths.get(prefix))) {
            return list.filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                    path -> path.toString().replace(prefix, ""), path -> path
            ));
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }
}
