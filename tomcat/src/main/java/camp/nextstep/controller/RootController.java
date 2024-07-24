package camp.nextstep.controller;

import camp.nextstep.http.ContentType;
import camp.nextstep.http.HttpRequest;
import camp.nextstep.http.HttpResponse;
import camp.nextstep.http.HttpStatus;
import camp.nextstep.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class RootController extends AbstractController{

    private static final String INDEX_PAGE = "index.html";
    private static final String STATIC_PATH = "static/";

    @Override
    protected HttpResponse doGet(HttpRequest request) throws Exception {
        return handleStaticFileRequest(INDEX_PAGE);
    }

    private HttpResponse handleStaticFileRequest(String filePath) {
        String extension = FileUtils.getFileExtension(filePath);
        URL url = getClass().getClassLoader().getResource(STATIC_PATH + filePath);

        if (url != null) {
            try {
                String responseBody = new String(Files.readAllBytes(new File(url.getFile()).toPath()));
                return HttpResponse.ok(responseBody, ContentType.getTypeByExtention(extension));
            } catch (IOException e) {
                return HttpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "File reading error");
            }
        }
        return HttpResponse.notFound("File not found");
    }
}
