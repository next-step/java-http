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
        return createResourceFromPath(
                requestLine.getPath().getUrlPath(),
                classLoader
        );
    }

    public static Resource createResourceFromPath(
            String path,
            ClassLoader classLoader
    ) {
        try {
            final File file = new File(getUri(path, classLoader));
            if (!file.exists()) {
                throw new ResourceNotFoundException("파일을 찾을 수 없습니다");
            }

            return new Resource(file);
        }  catch (Exception exception) {
            exception.printStackTrace();
            return new Resource(new File("static/404.html"));
        }
    }

    private static URI getUri(
            String path,
            ClassLoader classLoader
    ) throws URISyntaxException {
        return classLoader.getResource(ROOT.concat(path)).toURI();
    }
}
