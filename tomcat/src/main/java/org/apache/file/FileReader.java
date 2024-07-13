package org.apache.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    public static FileResource read(final Path path) throws IOException {
        var fileContent = new String(Files.readAllBytes(path));
        var mediaType = new MediaType(Files.probeContentType(path));

        return new FileResource(path, fileContent, fileContent.getBytes().length, mediaType);
    }

}
