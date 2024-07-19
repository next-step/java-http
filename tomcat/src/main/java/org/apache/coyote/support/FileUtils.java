package org.apache.coyote.support;

public final class FileUtils {

    private FileUtils() {
    }

    private static final String EMPTY_STRING = "";
    private static final String FILE_EXTENSION_DELIMITER = ".";

    public static String extractExtension(final String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return EMPTY_STRING;
        }

        int lastDotIndex = filePath.lastIndexOf(FILE_EXTENSION_DELIMITER);
        return filePath.substring(lastDotIndex + 1);
    }

}
