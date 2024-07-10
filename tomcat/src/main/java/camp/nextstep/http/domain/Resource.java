package camp.nextstep.http.domain;

import camp.nextstep.http.exception.ResourceNotFoundException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Resource {
    private File resourceFile;
    private static final String ROOT = "static";

    public File getResourceFile() {
        return resourceFile;
    }

    private Resource(File resourceFile) {
        this.resourceFile = resourceFile;
    }

    public static Resource createResourceFromRequestLine(
            RequestLine requestLine,
            ClassLoader classLoader
    ) {
        try {
            final File file = new File(
                    getUri(requestLine.getPath().getUrlPath(), classLoader)
            );
            if (!file.exists()) {
                throw new ResourceNotFoundException("파일을 찾을 수 없습니다");
            }

            return new Resource(file);
        }  catch (Exception exception) {
            exception.printStackTrace();
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }


    private static URI getUri(
            String path,
            ClassLoader classLoader
    ) throws URISyntaxException {
        return classLoader.getResource(ROOT.concat(path)).toURI();
    }
}
