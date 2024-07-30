package camp.nextstep.controller.strategy;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.coyote.http11.HttpEntity;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.HttpResponse;

public class RegisterPostStrategy implements RequestMethodStrategy {
    private static final Pattern DELIMITER = Pattern.compile("=");
    ;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
                .equals(RequestMethod.POST.name());
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        RequestBody requestBody = httpRequest.getRequestBody();

        Map<String, String> account = Arrays.stream(requestBody.getRequestBody()
                        .split("&"))
                .map(line -> DELIMITER.split(line))
                .collect(Collectors.toMap(words -> words[KEY_INDEX].trim(), words -> words[VALUE_INDEX].trim()));

        InMemoryUserRepository.save(new User(account.get("account"), account.get("password"), account.get("email")));

        return HttpEntity.redirect("/index.html");
    }
}
