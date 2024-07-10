package org.apache.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader {
    private static final String DEFAULT_PATH = "static";

    public static FileResource read(final String path) throws IOException {
        var resource = FileReader.class.getClassLoader().getResource(DEFAULT_PATH + path);
        var filePath = new File(resource.getFile()).toPath();
        var fileContent = new String(Files.readAllBytes(filePath));
        var mediaType = new MediaType(Files.probeContentType(filePath));

        return new FileResource(filePath, fileContent, fileContent.getBytes().length, mediaType);
    }

}
