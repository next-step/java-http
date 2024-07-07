package camp.nextstep.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class FileUtil {

    private static final String STATIC_FILE_PATH_PREFIX = "static";
    private static final String FILE_EXTENSION_DELIMITER = ".";

    private FileUtil() {
        throw new AssertionError();
    }

    public static String readStaticPathFileResource(String filePath, Class<?> clazz) throws IOException {
        File file = getFile(STATIC_FILE_PATH_PREFIX + filePath, clazz);
        return readResource(file);
    }

    private static File getFile(String filePath, Class<?> clazz) {
        URL resource = clazz.getClassLoader()
                .getResource(filePath);
        if (resource == null) {
            throw new IllegalArgumentException("존재하지 않는 file 경로입니다. - " + filePath);
        }
        return new File(resource.getFile());
    }

    private static String readResource(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    public static String parseExtension(String filePath) {
        if (!filePath.contains(FILE_EXTENSION_DELIMITER)) {
            throw new IllegalArgumentException("확장자 구분자가 존재하지 않아 확장자를 추출할 수 없습니다 - " + filePath);
        }
        return filePath.substring(filePath.lastIndexOf(FILE_EXTENSION_DELIMITER));
    }
}
