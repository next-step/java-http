package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceFinder {

    private static final String ROOT_RESOURCE_PATH = "static";
    private static final String LOGIN_PATH = "/login";
    private static final String LOGIN_RESOURCE_PATH = "/login.html";

    public String findContent(String httpPath) throws IOException {
        final URL resource = getResource(httpPath);
        return new String(Files.readAllBytes(getFilePath(resource)));
    }

    public Path findFilePath(String httpPath) {
        if(httpPath.equals(LOGIN_PATH)) {
            return getFilePath(getResource(LOGIN_RESOURCE_PATH));
        }

        final URL resource = getResource(httpPath);
        return getFilePath(resource);
    }

    private Path getFilePath(URL resource) {
        return new File(resource.getFile()).toPath();
    }

    private URL getResource(String httpPath) {
        final URL resource = getClass().getClassLoader().getResource(ROOT_RESOURCE_PATH + httpPath);
        if(resource == null) {
            throw new ResourceNotFoundException("not found resource: " + httpPath);
        }

        return resource;
    }

}
