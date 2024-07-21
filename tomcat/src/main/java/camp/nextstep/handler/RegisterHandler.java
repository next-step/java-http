package camp.nextstep.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.support.ResourceFinder;
import org.apache.coyote.http11.constants.HttpStatus;
import org.apache.coyote.http11.model.ContentType;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

import java.io.IOException;
import java.net.URL;

public class RegisterHandler extends AbstractController {

    private static final String REGISTER_RESOURCE_PATH = "/register.html";
    private static final String REDIRECT_PATH = "/index.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        registerUser(request);
        sendResponse(REDIRECT_PATH, response, HttpStatus.FOUND);
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        sendResponse(REGISTER_RESOURCE_PATH, response, HttpStatus.SUCCESS);
    }

    /**
     * 유저 등록
     *
     * @param request
     */
    private static void registerUser(HttpRequest request) {
        final String account = request.getBody().get("account");
        final String email = request.getBody().get("email");
        final String password = request.getBody().get("password");

        InMemoryUserRepository.save(new User(account, email, password));
    }

    /**
     * 응답 생성
     *
     * @param resourcePath 정적 리소스 파일 path
     * @param response http 응답
     * @param status http 상태 코드
     * @throws IOException
     */
    private static void sendResponse(String resourcePath, HttpResponse response, HttpStatus status) throws IOException {
        final URL resource = ResourceFinder.findResource(resourcePath);
        final String content = ResourceFinder.findContent(resource);
        response.setStatus(status);
        response.setContent(content);
        response.setContentType(ContentType.TEXT_HTML);
        response.send();
    }
}
