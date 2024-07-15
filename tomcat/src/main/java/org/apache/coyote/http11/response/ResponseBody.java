package org.apache.coyote.http11.response;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class ResponseBody {
    private final String responseBody;

    public ResponseBody(final String responseBody) {
        this.responseBody = responseBody;
    }

    static ResponseBody create(String urlPath) throws IOException {
        URL resource = createResource(urlPath);
        String responseBody = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
        return new ResponseBody(responseBody);
    }

    private static URL createResource(String filePath) {
        return ResponseBody.class.getClassLoader().getResource("static" + filePath);
    }
}
