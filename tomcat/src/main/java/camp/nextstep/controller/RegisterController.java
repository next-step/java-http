package camp.nextstep.controller;

import camp.nextstep.service.RegisterService;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;

public class RegisterController extends AbstractController {

    private static final String INDEX_PATH = "/index.html";
    private static final String NOT_FOUND_PATH = "/404.html";

    @Override
    protected void doPost(Request request, Response response) {
        RegisterService registerService = new RegisterService();
        try {
            registerService.registerUser(request);
            response.found(request.getCookies(), INDEX_PATH);
        } catch (Exception e) {
            response.notFound(ContentType.HTML, NOT_FOUND_PATH);
        }
    }
}
