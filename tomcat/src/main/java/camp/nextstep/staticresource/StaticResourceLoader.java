package camp.nextstep.staticresource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class StaticResourceLoader {
    public String readAllLines(String path) throws IOException {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            // TODO: 404 NOT FOUND exception
            throw new IllegalArgumentException("path 에 지정한 파일이 없습니다: " + path);
        }
        return readAllLines(resource);
    }

    public String readAllLines(URL resource) throws IOException {
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
