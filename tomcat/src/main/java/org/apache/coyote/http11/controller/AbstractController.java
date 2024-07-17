package org.apache.coyote.http11.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class AbstractController implements Controller{
	@Override
	public HttpResponse service(final HttpRequest request) throws Exception {
		if (request.isPost()) {
			return getPostResponse(request);
		}
		return getGetResponse(request);
	}

	protected HttpResponse getPostResponse(HttpRequest request) throws Exception {
		return HttpResponse.responseNotFound(request);
	}
	protected HttpResponse getGetResponse(HttpRequest request) throws Exception {
		return HttpResponse.responseNotFound(request);
	}
}
