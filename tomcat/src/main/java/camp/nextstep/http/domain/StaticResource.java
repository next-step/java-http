package camp.nextstep.http.domain;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.exception.ResourceNotFoundException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class StaticResource {
    private File resourceFile;
    private static final String ROOT = "static";

    public File getResourceFile() {
        return resourceFile;
    }

    private StaticResource(File resourceFile) {
        this.resourceFile = resourceFile;
    }

    public static StaticResource createResourceFromRequestLine(
            HttpRequest requestLine,
            ClassLoader classLoader
    ) {
        return createResourceFromPath(
                requestLine.getHttpStartLine().getPath().getUrlPath(),
                classLoader
        );
    }

    public static StaticResource createResourceFromPath(
            String path,
            ClassLoader classLoader
    ) {
        try {
            final File file = new File(getUri(path, classLoader));
            if (!file.exists()) {
                throw new ResourceNotFoundException("파일을 찾을 수 없습니다");
            }

            return new StaticResource(file);
        }  catch (Exception exception) {
            exception.printStackTrace();
            try {
                return new StaticResource(new File(getUri("/404.html", classLoader)));
            } catch (URISyntaxException e) {
                throw new ResourceNotFoundException("파일을 찾을 수 없습니다");
            }
        }
    }

    private static URI getUri(
            String path,
            ClassLoader classLoader
    ) throws URISyntaxException {
        return classLoader.getResource(ROOT.concat(path)).toURI();
    }
}
