package org.apache.coyote.http11.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.cookie.Cookie;
import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class LoginController extends AbstractController {

	@Override
	protected HttpResponse getGetResponse(final HttpRequest request) throws IOException {
		if (request.hasJSessionId()) {
			String jSessionId = request.getJSessionId();
			HttpSession jsessionid = SessionManager.getInstance().findSession(jSessionId);

			if (jsessionid != null) {
				return HttpResponse.redirectRoot(request);
			}
		}

		return HttpResponse.responseOkWithOutHtml(request);
	}

	@Override
	protected HttpResponse getPostResponse(final HttpRequest request) throws IOException {
		String account = request.getRequestBodyValueByKey("account");
		String password = request.getRequestBodyValueByKey("password");

		if (hasLoginSession(request.getCookies())) {
			return HttpResponse.redirectRoot(request);

		}

		final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NoSuchElementException::new);

		if (user.checkPassword(password)) {
			String uuid = UUID.randomUUID().toString();
			request.addCookie(new Cookie("JSESSIONID", uuid));
			Session session = new Session(uuid);
			session.setAttribute("user", user);
			SessionManager.getInstance().add(session);
			return HttpResponse.redirectRoot(request);
		}

		return HttpResponse.responseUnAuthorized(request);
	}

	private static boolean hasLoginSession(Cookies cookies) {
		if (cookies.hasJSessionId()) {
			String jSessionId = cookies.getJSessionId();
			return SessionManager.getInstance().isExistJSession(jSessionId);
		}
		return false;
	}
}

