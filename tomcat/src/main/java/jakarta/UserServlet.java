package jakarta;

import camp.nextstep.UserController;
import camp.nextstep.UserDto;
import camp.nextstep.service.UnauthroizedUserException;
import org.apache.catalina.SessionManager;
import org.apache.coyote.http11.FileLoader;
import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.MimeType;
import org.apache.coyote.http11.response.*;

import java.io.IOException;

public class UserServlet extends AbstractController {

    public static final AbstractController INSTANCE = new UserServlet();
    private final UserController userController = new UserController();
    private final SessionManager sessionManager = SessionManager.INSTANCE;


    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        var requestLine = httpServletRequest.getRequestLine();
        if (requestLine.getPath().equals("/login")) {
            final var viewModel = userController.getLogin(httpServletRequest);
            httpServletResponse.setStatusCode(HttpStatusCode.OK);
            httpServletResponse.setMimeType(MimeType.TEXT_HTML);
            httpServletResponse.setResponseBody(new MessageBody(FileLoader.read("static/" + viewModel.path())));
        }

        if (requestLine.getPath().equals("/register")) {
            final var viewModel = userController.register();
            httpServletResponse.setStatusCode(HttpStatusCode.OK);
            httpServletResponse.setMimeType(MimeType.TEXT_HTML);
            httpServletResponse.setResponseBody(new MessageBody(FileLoader.read("static/" + viewModel.path())));
        }
    }

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        var requestLine = httpServletRequest.getRequestLine();
        var session = httpServletRequest.getSession(false);

        if (requestLine.getPath().equals("/login")) {
            try {
                var viewModel = userController.login(httpServletRequest);
                session = viewModel.session();
                sessionManager.add(session);
                httpServletResponse.setStatusCode(HttpStatusCode.FOUND);
                httpServletResponse.setRedirectPath(viewModel.path());
                httpServletResponse.addCookie(HttpCookie.ofSessionId(session.getId()));
            } catch (UnauthroizedUserException e) {
                httpServletResponse.setStatusCode(HttpStatusCode.FOUND);
                httpServletResponse.setMimeType(MimeType.TEXT_HTML);
                httpServletResponse.setRedirectPath("/401.html");
            }
        }


        if (requestLine.getPath().equals("/register")) {
            var userDto = UserDto.map(httpServletRequest.getMessageBody().toMap());
            var viewModel = userController.register(userDto);
            httpServletResponse.setStatusCode(HttpStatusCode.FOUND);
            httpServletResponse.setMimeType(MimeType.TEXT_HTML);
            httpServletResponse.setRedirectPath(viewModel.path());
        }
    }
}
