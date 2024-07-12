package org.apache.coyote.support;

public final class FileUtils {

    private FileUtils() {
    }

    private static final String FILE_EXTENSION_DELIMITER = ".";

    public static String extractExtension(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }

        int lastDotIndex = filePath.lastIndexOf(FILE_EXTENSION_DELIMITER);
        return filePath.substring(lastDotIndex + 1);
    }

}
