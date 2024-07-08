package camp.nextstep.http.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Resource {

    private final String filePath;

    public Resource(final String filePath) {
        this.filePath = filePath;
    }

    public byte[] readAllBytes() throws IOException {
        final InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream(filePath);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(filePath);
        }
        return resourceAsStream.readAllBytes();
    }

    public boolean exists() {
        return ClassLoader.getSystemResource(filePath) != null;
    }
}
