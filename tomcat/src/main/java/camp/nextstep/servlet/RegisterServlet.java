package camp.nextstep.servlet;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import com.javax.servlet.http.HttpServlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.http.StatusCode;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;

import java.util.Objects;

public class RegisterServlet extends HttpServlet {
    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        StaticResource staticResource = StaticResourceResolver.findStaticResource("/register.html");
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final String account = httpRequest.getParameter("account");
        final String password = httpRequest.getParameter("password");
        final String email = httpRequest.getParameter("email");

        if (isInvalidUserInfo(account, password, email)) {
            StaticResource staticResource = StaticResourceResolver.findStaticResource("/register.html");
            httpResponse.setStatusCode(StatusCode.BAD_REQUEST);
            httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());

            return;
        }

        InMemoryUserRepository.save(new User(account, password, email));

        StaticResource staticResource = StaticResourceResolver.findStaticResource("/index.html");
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
    }

    private boolean isInvalidUserInfo(final String account, final String password, final String email) {
        return isInvalidInput(account) || isInvalidInput(password) || isInvalidInput(email);
    }

    private boolean isInvalidInput(final String input) {
        return Objects.isNull(input) || input.isBlank();
    }
}
