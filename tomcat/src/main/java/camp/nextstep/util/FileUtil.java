package camp.nextstep.util;

import java.io.File;
import java.net.URL;

public class FileUtil {

    private static final String STATIC_FILE_PATH_PREFIX = "static";

    private FileUtil() {
        throw new AssertionError();
    }

    public static File getStaticPathFile(String filePath, Class<?> clazz) {
        return getFile(STATIC_FILE_PATH_PREFIX + filePath, clazz);
    }

    private static File getFile(String filePath, Class<?> clazz) {
        URL resource = clazz.getClassLoader()
                .getResource(filePath);
        if (resource == null) {
            throw new IllegalArgumentException("존재하지 않는 file 경로입니다. - " + filePath);
        }
        return new File(resource.getFile());
    }
}
