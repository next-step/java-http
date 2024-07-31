package camp.nextstep.controller.strategy;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.coyote.http11.HttpEntity;
import org.apache.coyote.http11.MapUtil;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.HttpResponse;

public class RegisterPostStrategy implements RequestMethodStrategy {

    private static final Pattern DELIMITER = Pattern.compile("=");
    private static final String AMPERSEND = "&";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
            .equals(RequestMethod.POST.name());
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String requestBody = httpRequest.getRequestBody().getRequestBody();

        Map<String, String> account = MapUtil.parseToMap(requestBody.split(AMPERSEND), DELIMITER);

        InMemoryUserRepository.save(
            new User(account.get("account"), account.get("password"), account.get("email")));

        return HttpEntity.redirect("/index.html");
    }
}
