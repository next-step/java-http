package org.apache.coyote.http11;

import java.io.File;
import java.net.URL;

public class ResourceFinder {
    private static final String BASE_DIRECTORY = "static";

    public File findByPath(final String path) {
        URL resourceUrl = findResourceUrl(path);
        validate(resourceUrl);
        return new File(resourceUrl.getFile());
    }

    private URL findResourceUrl(final String path) {
        return getClass()
                .getClassLoader()
                .getResource(BASE_DIRECTORY + path);
    }

    private void validate(final URL resourceUrl) {
        if (resourceUrl == null) {
            throw new ResourceNotFoundException();
        }
    }
}