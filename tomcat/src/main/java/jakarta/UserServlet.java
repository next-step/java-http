package jakarta;

import camp.nextstep.UserController;
import camp.nextstep.service.UnauthroizedUserException;
import org.apache.catalina.Session;
import org.apache.catalina.SessionManager;
import org.apache.coyote.http11.FileLoader;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;
import org.apache.coyote.http11.request.HttpMethod;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.*;

import java.io.IOException;

public class UserServlet implements MyServlet {

    public static final MyServlet INSTANCE = new UserServlet();
    private final UserController userController = new UserController();
    private final SessionManager sessionManager = SessionManager.INSTANCE;

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
        Session session = request.getSession();
        HttpCookie cookie = request.getRequestHeaders().getCookie();
        org.apache.coyote.http11.request.MessageBody messageBody = request.getRequestBody();
        var httpServletRequest = new HttpServletRequest(session, Cookie.from(cookie), messageBody.toMap());

        if (requestLine.getPath().equals("/login")) {
            final var viewModel = userController.getLogin(httpServletRequest);
            response.setStatusLine(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.OK));
            response.setHttpResponseHeaders(new HttpResponseHeaders(MimeType.TEXT_HTML));
            response.setResponseBody(new MessageBody(FileLoader.read("static/" + viewModel.path())));
        }

        if (requestLine.getPath().equals("/register")) {
            final var viewModel = userController.register();
            response.setStatusLine(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.OK));
            response.setHttpResponseHeaders(new HttpResponseHeaders(MimeType.TEXT_HTML));
            response.setResponseBody(new MessageBody(FileLoader.read("static/" + viewModel.path())));
        }
    }

    private void doPost(HttpRequest request, HttpResponse response) {

        var requestLine = request.getRequestLine();
        var requestHeaders = request.getRequestHeaders();
        Session session = request.getSession();
        HttpCookie cookie = request.getRequestHeaders().getCookie();
        org.apache.coyote.http11.request.MessageBody messageBody = request.getRequestBody();
        HttpServletRequest httpServletRequest = new HttpServletRequest(session, Cookie.from(cookie), messageBody.toMap());

        if (requestLine.getPath().equals("/login")) {
            try {
                var viewModel = userController.login(httpServletRequest);
                var location = Location.of(requestLine.protocol(), requestHeaders.host(), viewModel.path());
                session = viewModel.session();
                sessionManager.add(session);
                response.setStatusLine(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.FOUND));
                response.setHttpResponseHeaders(new HttpResponseHeaders(MimeType.TEXT_HTML, location, HttpCookie.ofSessionId(session.getId())));
            } catch (UnauthroizedUserException e) {
                var location = Location.of(requestLine.protocol(), requestHeaders.host(), "/401.html");
                response.setStatusLine(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.FOUND));
                response.setHttpResponseHeaders(new HttpResponseHeaders(MimeType.TEXT_HTML, location));
            }
        }


        if (requestLine.getPath().equals("/register")) {
            var viewModel = userController.register(request.getRequestBody().toMap());
            var location = Location.of(requestLine.protocol(), requestHeaders.host(), viewModel.path());
            response.setStatusLine(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.FOUND));
            response.setHttpResponseHeaders(new HttpResponseHeaders(MimeType.TEXT_HTML, location));
        }

    }
}
