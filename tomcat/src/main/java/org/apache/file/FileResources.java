package org.apache.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class FileResources {
    private static final String DEFAULT_RESOURCE_PATH = "static";
    private static final String prefix = FileResources.class.getClassLoader().getResource(DEFAULT_RESOURCE_PATH).getPath();

    public static final Map<String, Path> resources = load();

    private static Map<String, Path> load() {
        try (var list = Files.walk(Paths.get(prefix))) {
            return list.filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            path -> path.toString().replace(prefix, ""), path -> path
                    ));
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    public static Path find(final String path) {
        return resources.get(path);
    }
}
