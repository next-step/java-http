package org.apache.file;

import org.apache.coyote.HttpResponse;
import org.apache.file.FileReader;
import org.apache.file.FileResources;
import org.apache.http.body.HttpFileBody;

import java.io.IOException;

public class ResourceHandler {

    public static HttpResponse handle(String path) throws IOException {
        final var filePath = FileResources.find(path);
        if (filePath == null) {
            return null;
        }
        final var file = FileReader.read(filePath);
        final var body = new HttpFileBody(file);

        return new HttpResponse(body);
    }
}
