package org.apache.coyote.http11.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;

public class ControllerResourceFactory implements ControllerFactory {

    public static final String ROOT_PATH = "/";
    private static final Map<String, String> extensionMapper = new HashMap<>(32);
    private static final String BASE_DIR = "static";
    private static final String DEFAULT_URL = BASE_DIR + "/index.html";

    public ControllerResourceFactory() {
        extensionMapper.put(".html", "text/html");
        extensionMapper.put(".css", "text/css");

    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String url = new StringBuilder()
            .append(BASE_DIR)
            .append(httpRequest.getRequestUrl())
            .toString();

        if (httpRequest.getRequestUrl().equals(ROOT_PATH)) {
            url = DEFAULT_URL;
        }

        try {
            final URL resource = getClass().getClassLoader()
                .getResource(url);
            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);
            final String extension =
                extensionMapper.keySet()
                    .stream()
                    .filter(ext -> file.getName().endsWith(ext))
                    .findAny()
                    .map(extensionMapper::get)
                    .orElseGet(() -> "*/*");
            return new Http11Response.HttpResponseBuilder()
                .responseHeader(extension, content.length)
                .statusLine(httpRequest.getVersion(), "OK")
                .messageBody(content)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


