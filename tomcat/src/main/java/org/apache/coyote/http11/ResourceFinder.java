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

    public static String findContent(final URL resource) throws IOException {
        return new String(Files.readAllBytes(getFilePath(resource)));
    }

    public static File findFile(final String httpPath) {
        if(httpPath.equals(LOGIN_PATH)) {
            return new File(findResource(LOGIN_RESOURCE_PATH).getFile());
        }

        return new File(findResource(httpPath).getFile());
    }

    public static URL findResource(final String httpPath) {
        String filePath = httpPath;
        if(httpPath.equals(LOGIN_PATH)) {
            filePath = LOGIN_RESOURCE_PATH;
        }

        final URL resource = ResourceFinder.class.getClassLoader().getResource(ROOT_RESOURCE_PATH + filePath);
        if(resource == null) {
            throw new ResourceNotFoundException("not found resource: " + httpPath);
        }

        return resource;
    }

    private static Path getFilePath(final URL resource) {
        return new File(resource.getFile()).toPath();
    }

}
