package org.apache.coyote.http11.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.model.ContentType;
import org.apache.coyote.http11.response.HttpResponse;

import java.io.IOException;

public class RegisterController extends AbstractController {

	@Override
	protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
		response.setContentType(ContentType.TEXT_HTML);
		response.forward("/register.html");

	}

	@Override
	protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
		String account = request.getRequestBodyValueByKey("account");
		String password = request.getRequestBodyValueByKey("password");
		String email = request.getRequestBodyValueByKey("email");

		InMemoryUserRepository.save(new User(account, password, email));

		response.sendRedirect("/index.html");
	}
}
