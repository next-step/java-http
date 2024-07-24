package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.http.ContentType;
import camp.nextstep.http.HttpRequest;
import camp.nextstep.http.HttpResponse;
import camp.nextstep.http.HttpStatus;
import camp.nextstep.model.User;
import camp.nextstep.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class RegisterController extends AbstractController{

    private static final String STATIC_PATH = "static/";
    private static final String INDEX_PATH = "/index.html";
    private static final String REGISTER_PAGE = "register.html";

    @Override
    protected HttpResponse doPost(HttpRequest request) throws Exception {
        return handlePostRegisterRequest(request);
    }

    @Override
    protected HttpResponse doGet(HttpRequest request) throws Exception {
        return handleStaticFileRequest(REGISTER_PAGE);
    }

    private HttpResponse handlePostRegisterRequest(HttpRequest httpRequest) {

        final User user = new User(httpRequest.getRequestBody().getValue("account"),
                httpRequest.getRequestBody().getValue("password"),
                httpRequest.getRequestBody().getValue("email"));
        InMemoryUserRepository.save(user);

        return HttpResponse.redirect(INDEX_PATH);
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
