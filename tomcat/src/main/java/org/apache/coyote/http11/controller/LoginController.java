package org.apache.coyote.http11.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.cookie.Cookie;
import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.model.ContentType;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class LoginController extends AbstractController {

	@Override
	protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
		if (request.hasJSessionId()) {
			String jSessionId = request.getJSessionId();
			HttpSession jsessionid = SessionManager.getInstance().findSession(jSessionId);

			if (jsessionid != null) {
				response.sendRedirect("/index.html");
				return;
			}
		}

		response.setContentType(ContentType.TEXT_HTML);
		response.forward("/login.html");
	}

	@Override
	protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
		String account = request.getRequestBodyValueByKey("account");
		String password = request.getRequestBodyValueByKey("password");

		if (hasLoginSession(request.getCookies())) {
			response.sendRedirect("/index.html");
			return;
		}

		final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NoSuchElementException::new);

		if (user.checkPassword(password)) {
			String uuid = UUID.randomUUID().toString();
			request.addCookie(new Cookie("JSESSIONID", uuid));
			Session session = new Session(uuid);
			session.setAttribute("user", user);
			SessionManager.getInstance().add(session);

			response.sendRedirect("/index.html");
			return;
		}

		response.sendRedirect("/401.html");
	}

	private static boolean hasLoginSession(Cookies cookies) {
		if (cookies.hasJSessionId()) {
			String jSessionId = cookies.getJSessionId();
			return SessionManager.getInstance().isExistJSession(jSessionId);
		}
		return false;
	}
}

