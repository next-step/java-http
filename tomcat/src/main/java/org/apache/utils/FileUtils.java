package org.apache.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.http11.request.HttpPath;

public class FileUtils {

    private static final String STATIC_PATH = "static";

    private FileUtils() {
    }

    public static String getStaticFileContent(HttpPath path) throws IOException {
        String resourcePath = ClassLoader.getSystemResource(STATIC_PATH).getPath();
        return readFileContent(resourcePath + path.getFilePath());
    }

    public static String readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return StringUtils.EMPTY;
        }
        return Files.readString(file.toPath());
    }
}
