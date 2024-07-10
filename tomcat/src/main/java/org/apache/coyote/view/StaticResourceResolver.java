package org.apache.coyote.view;

import org.apache.coyote.http.ContentType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticResourceResolver {

    private static final String STATIC_RESOURCE_PATH = "static/";

    public static StaticResource findStaticResource(final String filePath) throws IOException {
        final URL resource = StaticResourceResolver.class.getClassLoader().getResource(STATIC_RESOURCE_PATH + filePath);
        final File file = new File(resource.getFile());

        if (!file.isFile()) {
            throw new StaticResourceNotFoundException();
        }

        final Path path = file.toPath();
        final String content = new String(Files.readAllBytes(path));
        final String mimeType = Files.probeContentType(path);

        return new StaticResource(content, ContentType.from(mimeType));
    }
}
