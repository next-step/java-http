package org.apache.coyote.support;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ResourceFinder {

    private static final String ROOT_RESOURCE_PATH = "static";

    private ResourceFinder() {
    }

    public static String findContent(final URL resource) throws IOException {
        return new String(Files.readAllBytes(getFilePath(resource)));
    }

    public static File findFile(final String httpPath) {
        return new File(findResource(httpPath).getFile());
    }

    public static URL findResource(final String httpPath) {
        final URL resource = ResourceFinder.class.getClassLoader().getResource(ROOT_RESOURCE_PATH + httpPath);
        if(resource == null) {
            throw new ResourceNotFoundException("not found resource: " + httpPath);
        }

        return resource;
    }

    private static Path getFilePath(final URL resource) {
        return new File(resource.getFile()).toPath();
    }

}
