package jakarta;

import camp.nextstep.UserController;
import camp.nextstep.service.UnauthroizedUserException;
import org.apache.coyote.http11.FileLoader;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;
import org.apache.coyote.http11.constants.HttpCookies;
import org.apache.coyote.http11.request.HttpMethod;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class UserServlet implements MyServlet {

    public static final MyServlet INSTANCE = new UserServlet();
    private final UserController userController = new UserController();

    @Override
    public void delegate(HttpRequest request, HttpResponse response) throws IOException {

        final var requestLine = request.getRequestLine();

        if (HttpMethod.GET == requestLine.getMethod()) {
            doGet(request, response);
        }
        if (HttpMethod.POST == requestLine.getMethod()) {
            doPost(request, response);
        }
    }

    private void doGet(HttpRequest request, HttpResponse response) throws IOException {
        var requestLine = request.getRequestLine();

        if (requestLine.getPath().equals("/login")) {
            final var viewModel = userController.login();
            response.setResponse(HttpStatusCode.OK, new HttpResponseHeaders(MimeType.TEXT_HTML), new ResponseBody(FileLoader.read("static/" + viewModel.path())));
        }

        if (requestLine.getPath().equals("/register")) {
            final var viewModel = userController.register();
            response.setResponse(HttpStatusCode.OK, new HttpResponseHeaders(MimeType.TEXT_HTML), new ResponseBody(FileLoader.read("static/" + viewModel.path())));
        }
    }

    private void doPost(HttpRequest request, HttpResponse response) {

        var requestLine = request.getRequestLine();
        var requestHeaders = request.getRequestHeaders();

        if (requestLine.getPath().equals("/login")) {
            Map<String, Object> requestBodyMap = request.getRequestBody().toMap();
            try {
                var viewModel = userController.login(requestBodyMap);
                var location = Location.of(requestLine.protocol(), requestHeaders.host(), viewModel.path());

                if (!requestHeaders.hasCookie(HttpCookies.JSESSIONID)) {
                    var httpCookie = new HttpCookie();
                    httpCookie.addSessionId(UUID.randomUUID());
                    response.setResponse(HttpStatusCode.OK, new HttpResponseHeaders(MimeType.TEXT_HTML, location, httpCookie));
                    return;
                }

                var httpCookie = requestHeaders.getCookie();
                response.setResponse(HttpStatusCode.FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML, location, httpCookie));
            } catch (UnauthroizedUserException e) {
                var location = Location.of(requestLine.protocol(), requestHeaders.host(), "/401.html");
                response.setResponse(HttpStatusCode.FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML, location));
            }
        }


        if (requestLine.getPath().equals("/register")) {
            var viewModel = userController.register(request.getRequestBody().toMap());
            var location = Location.of(requestLine.protocol(), requestHeaders.host(), viewModel.path());
            response.setResponse(HttpStatusCode.FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML, location));
        }

    }
}
