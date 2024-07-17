package org.apache.coyote.http11.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import java.io.IOException;

public class RootController extends AbstractController {

	@Override
	protected HttpResponse getGetResponse(final HttpRequest request) throws IOException {
		return HttpResponse.redirect(request,"/index.html");
	}
}
