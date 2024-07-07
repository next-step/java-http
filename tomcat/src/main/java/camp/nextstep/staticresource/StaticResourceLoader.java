package camp.nextstep.staticresource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

// XXX: test
public class StaticResourceLoader {
    public String readAllLines(String path) throws IOException {
        URL resource = getClass().getClassLoader().getResource("static" + path);
        if (resource == null) {
            // TODO: NotFoundException?
            throw new IllegalArgumentException("path 에 지정한 파일이 없습니다: " + path);
        }
        return readAllLines(resource);
    }

    public String readAllLines(URL resource) throws IOException {
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
