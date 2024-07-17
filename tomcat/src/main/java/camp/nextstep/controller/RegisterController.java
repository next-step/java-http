package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.request.RequestBody;
import org.apache.coyote.response.FileFinder;
import org.apache.coyote.response.HttpResponse;
import org.apache.coyote.response.HttpStatus;
import org.apache.coyote.response.MimeType;

public class RegisterController extends AbstractController {

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        return new HttpResponse(
                HttpStatus.OK,
                MimeType.HTML,
                FileFinder.find("/register.html"));
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        RequestBody requestBody = request.getRequestBody();
        User user = new User(requestBody.get("account"), requestBody.get("password"), requestBody.get("email"));
        InMemoryUserRepository.save(user);

        return new HttpResponse(
                HttpStatus.CREATED,
                MimeType.HTML,
                FileFinder.find("/index.html"));
    }
}
