package camp.nextstep.controller.strategy;

import camp.nextstep.controller.session.Session;
import camp.nextstep.controller.session.SessionManager;
import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.exception.UnAuthorizedException;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.header.Http11ResponseHeader.HttpResponseHeaderBuilder;
import org.apache.coyote.http11.response.statusline.ProtocolVersion;
import org.apache.coyote.http11.response.statusline.StatusCode;

import java.util.Map;
import java.util.UUID;

public class LoginGetStrategy implements RequestMethodStrategy {

    public static final String INDEX_HTML = "/index.html";
    public static final String GET = "GET";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
                .equals(RequestMethod.GET.name())
                && !httpRequest.getParams().isEmpty();
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        Map<String, String> userParams = httpRequest.getParams();

        User user = login(userParams);

        Map<String, String> session = httpRequest
                .getSession()
                .orElseGet(() -> Map.of("JSESSIONID", startSession(user).getId()));

        Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .location(INDEX_HTML)
                .cookie(session)
                .build();

        return new Http11Response.HttpResponseBuilder()
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.FOUND.name())
                .responseHeader(responseHeader)
                .build();
    }

    private User login(Map<String, String> userParams) {
        User user = InMemoryUserRepository.findByAccount(userParams.get(ACCOUNT))
                .orElseThrow(() -> new UnAuthorizedException("존재하지 않는 사용자 입니다."));

        user.checkPassword(userParams.get(PASSWORD));

        return user;
    }

    private Session startSession(User user) {
        final Session session = new Session(UUID.randomUUID(), user);
        SessionManager.add(session);
        return session;
    }
}
