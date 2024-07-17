package org.apache.coyote.http11.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import java.io.IOException;

public class RegisterController extends AbstractController {

	@Override
	protected HttpResponse getGetResponse(final HttpRequest request) throws IOException {
		return HttpResponse.responseOkWithOutHtml(request);
	}

	@Override
	protected HttpResponse getPostResponse(final HttpRequest request) throws IOException {
		String account = request.getRequestBodyValueByKey("account");
		String password = request.getRequestBodyValueByKey("password");
		String email = request.getRequestBodyValueByKey("email");

		InMemoryUserRepository.save(new User(account, password, email));

		return HttpResponse.responseOk(request);
	}
}
