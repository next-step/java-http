package camp.nextstep.controller.strategy;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.util.Map;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;

public class RegisterPostStrategy implements RequestMethodStrategy {



    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
            .equals(RequestMethod.POST.name());
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        Map<String, String> requestBody = httpRequest.getRequestBody().getRequestBody();

        InMemoryUserRepository.save(
            new User(requestBody.get("account"), requestBody.get("password"), requestBody.get("email")));

        return Http11Response.redirect("/index.html");
    }
}
