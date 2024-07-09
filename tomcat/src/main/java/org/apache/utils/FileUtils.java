package org.apache.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.lang3.StringUtils;

public class FileUtils {

    private FileUtils() {
    }

    public static String readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return StringUtils.EMPTY;
        }
        return Files.readString(file.toPath());
    }
}
